package practice.wanted_notion.page.web.response;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Builder;
import lombok.Getter;
import practice.wanted_notion.page.domain.Page;

@Getter
public class PageResponse {

	private final String name;
	private final String content;
	private final List<String> breadcrumb;

	@Builder
	private PageResponse(String name, String content, List<String> breadcrumb) {
		this.name = name;
		this.content = content;
		this.breadcrumb = breadcrumb;
	}

	public static PageResponse of(Page page) {
		String breadcrumbs = page.getBreadcrumb();

		return PageResponse.builder()
			.name(page.getName())
			.content(page.getContent())
			.breadcrumb(getBreadcrumbList(breadcrumbs))
			.build();
	}

	private static List<String> getBreadcrumbList(String breadcrumb) {
		return Arrays.stream(breadcrumb.split("/")).collect(Collectors.toList());
	}
}
