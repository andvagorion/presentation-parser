package net.stefangaertner.html.element;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public abstract class AbstractElement implements HTMLElement {

	private HTMLElement parent;
	private List<HTMLElement> children;
	protected List<HTMLAttribute> attributes;

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.stefangaertner.html.element.HTMLElement#getTagName()
	 */
	@Override
	public abstract String getTagName();

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.stefangaertner.html.element.HTMLElement#addAttribute(net.
	 * stefangaertner.html.element.HTMLAttribute)
	 */
	@Override
	public HTMLElement addAttribute(HTMLAttribute attribute) {
		if (this.attributes == null) {
			this.attributes = new ArrayList<HTMLAttribute>();
		}

		this.attributes.add(attribute);
		return this;
	}
	
	/* (non-Javadoc)
	 * @see net.stefangaertner.html.element.HTMLElement#addAttribute(java.lang.String, java.lang.String)
	 */
	@Override
	public HTMLElement addAttribute(String name, String value) {
		return this.addAttribute(new HTMLAttribute(name, value));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.stefangaertner.html.element.HTMLElement#getAttributes()
	 */
	@Override
	public List<HTMLAttribute> getAttributes() {
		return this.attributes;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.stefangaertner.html.element.HTMLElement#isInline()
	 */
	@Override
	public boolean isInline() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.stefangaertner.html.element.HTMLElement#needsToBeClosed()
	 */
	@Override
	public boolean needsToBeClosed() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.stefangaertner.html.element.HTMLElement#getChildren()
	 */
	@Override
	public List<HTMLElement> getChildren() {
		return children;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.stefangaertner.html.element.HTMLElement#setChildren(java.util.List)
	 */
	@Override
	public HTMLElement setChildren(List<HTMLElement> children) {
		this.children = children;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.stefangaertner.html.element.HTMLElement#getParent()
	 */
	@Override
	public HTMLElement getParent() {
		return parent;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.stefangaertner.html.element.HTMLElement#setParent(net.stefangaertner.
	 * html.element.HTMLElement)
	 */
	@Override
	public HTMLElement setParent(HTMLElement parent) {
		this.parent = parent;
		return this;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getTagName());
		
		if (this.hasAttributes()) {
			StringJoiner sj = new StringJoiner(", ", "[", "]");
			for (HTMLAttribute attribute : this.attributes) {
				sj.add(attribute.toString());
			}
			sb.append(sj.toString());
		}
		
		return sb.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.stefangaertner.html.element.HTMLElement#addChild(net.stefangaertner.
	 * html.element.AbstractElement)
	 */
	@Override
	public HTMLElement addChild(HTMLElement child) {
		if (this.children == null) {
			this.children = new ArrayList<>();
		}
		this.children.add(child);
		child.setParent(this);
		return child;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.stefangaertner.html.element.HTMLElement#addChildren(java.util.List)
	 */
	@Override
	public HTMLElement addChildren(List<HTMLElement> children) {
		HTMLElement current = null;
		for (HTMLElement child : children) {
			addChild(child);
		}
		return current;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.stefangaertner.html.element.HTMLElement#getRoot()
	 */
	@Override
	public HTMLElement getRoot() {
		HTMLElement retVal = this;
		while (retVal.getParent() != null) {
			retVal = retVal.getParent();
		}
		return retVal;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.stefangaertner.html.element.HTMLElement#hasAttributes()
	 */
	@Override
	public boolean hasAttributes() {
		return attributes != null && !attributes.isEmpty();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.stefangaertner.html.element.HTMLElement#getParent(java.lang.Class)
	 */
	@Override
	public HTMLElement getParent(Class<?> clazz) {
		HTMLElement element = this;
		while (!clazz.isInstance(element) && element.getParent() != null) {
			element = element.getParent();
		}
		if (clazz.isInstance(element)) {
			return element;
		} else {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.stefangaertner.html.element.HTMLElement#checkDepth(java.lang.Class)
	 */
	@Override
	public int determineDepth(Class<? extends HTMLElement> listType) {
		HTMLElement element = this;
		int depth = 0;
		while (listType.isInstance(element)) {
			depth++;
			element = element.getParent();
		}
		return depth;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.stefangaertner.html.element.HTMLElement#hasChildren()
	 */
	@Override
	public boolean hasChildren() {
		return this.children != null && !this.children.isEmpty();
	}

	@Override
	public boolean checkParents(Class<?>[] classes) {
		HTMLElement element = this;
		for (int i = 0; i < classes.length; i++) {
			if (element == null || !classes[i].isInstance(element)) {
				return false;
			}
			element = element.getParent();
		}
		return true;
	}

}