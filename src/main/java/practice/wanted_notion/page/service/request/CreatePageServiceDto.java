package practice.wanted_notion.page.service.request;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CreatePageServiceDto {

	private final String name;
	private final String content;
	private final List<String> breadcrumb;

	@Builder
	private CreatePageServiceDto(String name, String content, List<String> breadcrumb) {
		this.name = name;
		this.content = content;
		this.breadcrumb = breadcrumb;
	}
}
