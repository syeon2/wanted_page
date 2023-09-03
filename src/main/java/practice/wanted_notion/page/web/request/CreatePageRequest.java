package practice.wanted_notion.page.web.request;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import practice.wanted_notion.page.service.request.CreatePageServiceDto;

@Getter
public class CreatePageRequest {

	private final String name;
	private final String content;
	private final List<String> breadcrumbs;

	@Builder
	private CreatePageRequest(String name, String content, List<String> breadcrumbs) {
		this.name = name;
		this.content = content;
		this.breadcrumbs = breadcrumbs;
	}

	public CreatePageServiceDto toServiceRequest() {
		return CreatePageServiceDto.builder()
			.name(this.name)
			.content(this.content)
			.breadcrumb(this.breadcrumbs)
			.build();
	}
}
