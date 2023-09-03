package practice.wanted_notion.page.dao;

import practice.wanted_notion.page.domain.Page;

public interface PageRepository {

	Long save(Page page);

	Page findById(Long id);
}
