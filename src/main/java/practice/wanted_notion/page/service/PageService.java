package practice.wanted_notion.page.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import practice.wanted_notion.page.dao.PageRepository;
import practice.wanted_notion.page.domain.Page;
import practice.wanted_notion.page.service.request.CreatePageServiceDto;
import practice.wanted_notion.page.web.response.PageResponse;

@Service
@RequiredArgsConstructor
public class PageService {

	private final PageRepository pageRepository;

	public Long createPage(CreatePageServiceDto createPageServiceDto) {
		Page page = Page.toDomain(createPageServiceDto);

		return pageRepository.save(page);
	}

	public PageResponse getPage(Long id) {
		Page findPage = pageRepository.findById(id);
		List<Page> subPages = pageRepository.findByPrevId(id);

		return PageResponse.of(findPage, subPages);
	}
}
