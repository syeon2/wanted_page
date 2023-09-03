package practice.wanted_notion.page.dao.mybatis;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import practice.wanted_notion.page.domain.Page;

@Mapper
public interface PageMapper {

	void save(Page page);

	Optional<Page> findById(Long id);

	List<Page> findByPrevId(Long prevId);
}
