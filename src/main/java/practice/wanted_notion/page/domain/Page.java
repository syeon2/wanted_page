package practice.wanted_notion.page.domain;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Builder;
import lombok.Getter;
import practice.wanted_notion.page.service.request.CreatePageServiceDto;

@Getter
public class Page {

	private Long id;
	private final String name;
	private final String content;
	private final String breadcrumb;

	@Builder
	private Page(String name, String content, String breadcrumb) {
		this.name = name;
		this.content = content;
		this.breadcrumb = breadcrumb;
	}

	public static Page toDomain(CreatePageServiceDto createPageServiceDto) {
		return Page.builder()
			.name(createPageServiceDto.getName())
			.content(createPageServiceDto.getContent())
			.breadcrumb(createBreadcrumb(createPageServiceDto))
			.build();
	}

	private static String createBreadcrumb(CreatePageServiceDto createPageServiceDto) {
		List<String> breadcrumbs = createPageServiceDto.getBreadcrumb();

		return String.join("/", breadcrumbs);
	}
}
