package practice.wanted_notion.page.dao.mybatis;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import practice.wanted_notion.page.dao.PageRepository;
import practice.wanted_notion.page.domain.Page;

@Repository
@RequiredArgsConstructor
public class MybatisPageRepository implements PageRepository {

	private final PageMapper pageMapper;

	@Override
	public Long save(Page page) {
		pageMapper.save(page);

		return page.getId();
	}

	@Override
	public Page findById(Long id) {
		return pageMapper.findById(id)
			.orElseThrow(() -> new NoSuchElementException("해당 페이지는 존재하지 않습니다."));
	}
}
