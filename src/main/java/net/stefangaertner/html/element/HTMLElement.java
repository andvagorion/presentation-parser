package net.stefangaertner.html.element;

import java.util.List;

public interface HTMLElement {

	String getTagName();

	HTMLElement addAttribute(HTMLAttribute attribute);

	List<HTMLAttribute> getAttributes();

	boolean isInline();

	boolean needsToBeClosed();

	List<HTMLElement> getChildren();

	HTMLElement setChildren(List<HTMLElement> children);

	HTMLElement getParent();

	HTMLElement setParent(HTMLElement parent);

	HTMLElement addChild(HTMLElement htmlElement);

	HTMLElement addChildren(List<HTMLElement> children);

	HTMLElement getRoot();

	boolean hasAttributes();

	HTMLElement getParent(Class<?> clazz);

	int determineDepth(Class<? extends HTMLElement> listType);

	boolean hasChildren();

	boolean checkParents(Class<?>[] classes);

	HTMLElement addAttribute(String key, String value);

}