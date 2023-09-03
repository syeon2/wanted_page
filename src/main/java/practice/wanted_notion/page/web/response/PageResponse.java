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
	private final List<Page> subPageList;
	private final List<String> breadcrumb;

	@Builder
	private PageResponse(String name, String content, List<Page> subPageList, List<String> breadcrumb) {
		this.name = name;
		this.content = content;
		this.subPageList = subPageList;
		this.breadcrumb = breadcrumb;
	}

	public static PageResponse of(Page page, List<Page> subPageList) {
		String breadcrumbs = page.getBreadcrumb();

		return PageResponse.builder()
			.name(page.getName())
			.content(page.getContent())
			.subPageList(subPageList)
			.breadcrumb(getBreadcrumbList(breadcrumbs))
			.build();
	}

	private static List<String> getBreadcrumbList(String breadcrumb) {
		return Arrays.stream(breadcrumb.split("/")).collect(Collectors.toList());
	}
}
