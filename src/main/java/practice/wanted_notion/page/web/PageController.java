package practice.wanted_notion.page.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import practice.wanted_notion.page.service.PageService;
import practice.wanted_notion.page.web.request.CreatePageRequest;
import practice.wanted_notion.page.web.response.PageResponse;

@RestController
@RequiredArgsConstructor
public class PageController {

	private final PageService pageService;

	@PostMapping("/api/v1/page")
	public Long createPage(@RequestBody CreatePageRequest createPageRequest) {
		return pageService.createPage(createPageRequest.toServiceRequest());
	}

	@GetMapping("/api/v1/page/{id}")
	public PageResponse getPage(@PathVariable Long id) {
		return pageService.getPage(id);
	}
}
