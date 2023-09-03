## 🌱 원티드 인턴쉽 2주차 과제

### 🟤 노션 페이지의 Breadcrumb을 고려하여 page API를 만들기

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

=> 조인 쿼리로 쿼리를 줄일 수 있을 것 같다. 조인문으로 개선하기

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
