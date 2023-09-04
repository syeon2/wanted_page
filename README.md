## 🌱 원티드 인턴쉽 2주차 과제

### 🟤 노션 페이지의 Breadcrumb을 고려하여 page API를 만들기

### 팀 이름 / 팀원
- 팀 이름 : 면접 9팀
- 참여 팀원 : 김수연, 이성주, 이새힘, 최민석

---

#### DB 모델링

<img src="https://i.ibb.co/DDqhc7n/wanted-page-assignment.png" alt="wanted-page-assignment" width="800">

---

#### 쿼리

1. 생성 쿼리

> insert into page (name, content, breadcrumb, prev_page_id) values (?, ?, ?, ?);


2. 페이지 조회 쿼리

> select id, name, content, breadcrumb, prev_page_id from page where id = ?;
> 
> select id, name, content, breadcrumb, prev_page_id from page where prev_page_id = ?;

------

#### 클라이언트 제공 JSON 구조

```json
{
    "name": "one",
    "content": "test",
    "subPageList": [
        {
            "id": 3,
            "name": "two",
            "content": "test",
            "breadcrumb": "one/two",
            "prevPageId": 1
        }
    ],
    "breadcrumb": [
        "first"
    ]
}
```

----

#### ❗️ 해당 구현이 최선이라고 생각하는 이유

#### 1. 모델링

Notion의 페이지 형태는 하나의 상위 페이지에 다수의 하위 페이지가 존재하는 방식으로 구현되어 있습니다. 또한 하위 페이지의 depth는 제한이 없고, 하위 페이지를
무한히 개설할 수 있다는 것을 주요 특징으로 삼았습니다.

모델링의 경우 Page 테이블 단독으로 있으며 자기 자신을 1:N 연관관계를 가지고 있습니다. 이는 상위 페이지 1개가 여러 개의 하위 페이지를 가질 수 있다는 것에 초점을 맞추어
모델링을 진행하였습니다.

page column 중 prev_page_id 값은 현재 페이지가 가지고 있는 하위 페이지를 모두 조회하기 위한 컬럼입니다. 반대로 next_page_id값도 필요하지 않을까? 
고려할 수도 있지만 하위 페이지가 역으로 상위 페이지를 조회하는 기능은 노션에서 발견하지 못하여 next_page_id 컬럼이 존재하는 것은 무의미합니다. 
따라서 각 페이지는 자신의 상위 페이지가 어떤 페이지인지 알고 있습니다.
<br />
(단, 최상위 페이지(루트 페이지)는 더이상 자신보다 위의 페이지가 존재하지 않기 때문에 0값을 임시로 갖습니다.)

.....

#### 2. Breadcrumb

Breadcrumb는 각 이전 페이지 이력을 나타내는 값입니다. 아무런 최적화 없이 단순하게 생각해보면 현재 페이지를 기준으로 이전에 접근할 수 있는 페이지의 
이력을 prev_page_id로 조회하여 현재 페이지까지 도달할 수 있는 이력을 조회할 수 있습니다.

하지만 극단적으로 생각해보면 매우 최적화가 필요하다고 여길 수 있습니다. 바로 현재 페이지까지 도달하기 위한 이전 페이지를 100번 거쳐야하면 쿼리를 100번 요청해야하는 문제입니다.

```
public Optional<Page> findById(Long id){
	String sql = "SELECT id, title, parent_page_id, content FROM page WHERE id = :id";

	SqlParameterSource params = new MapSqlParameterSource("id",id);

	Page page = jdbcTemplate.queryForObject(sql,params,new PageMapper());

	if (page.getParentPage() != null && page.getParentPage() != 0) {
	    // 이전 페이지가 존재한다면 재귀 호출
    	Page parentPage = findById(page.getParentPage()).orElseThrow();
    	
    	page.updateParent(parentPage);
	}
}
```

위의 코드처럼 자신보다 상위의 페이지가 존재한다면 findById 함수를 다시 호출하여 DB에 쿼리를 요청하는 방법입니다. 코드는 매우 직관적이지만, 앞서 언급된대로 
상위 페이지 개수만큼 쿼리를 요청해야하는 단점을 가지고 있습니다.

현 상황을 개선하기 위해 현재 페이지에 대한 페이지 이력은 현 페이지의 DB 테이블에 저장하는 것을 고려하였습니다. 바로, Breadcrumb를 String값으로 직렬화하여 저장하는 방법입니다. 
새로운 하위 페이지가 개설되면 현재 페이지에서 가지고 있는 breadcrumb 데이터에서 구분자 "/"와 생성한 하위 페이지의 이름을 함께 저장하는 방법입니다. 
가령, 첫 페이지가 first 제목을 가지고 있다면 first를 breadcrumb 컬럼에 저장합니다. 이후 first 제목의 페이지에서 하위 페이지를 생성하면 하위 페이지는 
"first/하위페이지 이름" 값을 하위 페이지의 breadcrumb 데이터에 저장합니다.

해당 값을 통해 다시 클라이언트에게 현재 페이지에 대한 이력을 모두 전송할 때는 "/"를 기준으로 split한 이후에 생성된 배열을 클라이언트에게 전달해주는 것으로 
쿼리 요청 문제를 해결하였습니다.

```
private static List<String> getBreadcrumbList(String breadcrumb) {
        // "first/second/third"같이 /로 구분되어 있는 상위 페이지 이력을 배열로 만들어 반환합니다.
        
		return Arrays.stream(breadcrumb.split("/")).collect(Collectors.toList());
	}
```
