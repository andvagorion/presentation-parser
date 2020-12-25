"use strict";

NodeList.prototype.getByClassName = function(className) {
	for (let i = 0; i < this.length; i++) {
		if (this[i].className == className) {
			return this[i];
		}
	}
};

Element.prototype.show = function() {
	this.style.display = "block";
	this.style.opacity = 1;
};

Element.prototype.hide = function() {
	this.style.opacity = 0;
	this.addEventListener("transitionend", this.afterHide, false);
};

Element.prototype.afterHide = function() {
	this.style.display = "none";
	this.removeEventListener("transitionend", this.afterHide);
};

Element.prototype.toggleClass = function(className) {
	if (this.classList.contains(className)) {
		this.classList.remove(className);
	} else {
		this.classList.add(className);
	}
};

Function.prototype.debounce = function(delay) {
  var fn = this
  return function() {
    fn.args = arguments
    fn.timeout_id && clearTimeout(fn.timeout_id)
    fn.timeout_id = setTimeout(function() { return fn.apply(fn, fn.args) }, delay)
  }
};

var Prez = (function() {

	var sheets = [],
		total = 0,
		currentIndex = 0,
		progressDiv = null,
		waitBox = null;

	function init() {
		
		sheets = document.getElementsByClassName("sheet");
		progressDiv = document.getElementById("progressBar");
		waitBox = document.getElementById("waitBox");
		
		if (sheets.length < 1) {
			return;
		}
		total = sheets.length;
		
		updateView();
		
		window.addEventListener("resize", function() {
			updateView();
		}.debounce(250));
		
		initNavigation();
		
	};
	
	function initNavigation() {
		
		window.onkeydown = function(event) {

			var keyDownEvent = event || window.event, 
				keycode = (keyDownEvent.which) ? keyDownEvent.which : keyDownEvent.keyCode;

			switch (keyDownEvent.key) {
				case "ArrowLeft":
					previousSlide();
					break;
				case "ArrowRight":
					nextSlide();
					break;
			}
		}
		
		document.getElementById("pager").addEventListener("click", function() {
			this.nextElementSibling.toggleClass("hidden");
		});
	};
	
	function nextSlide() {

		transition(currentIndex, Math.min(currentIndex + 1, total - 1));
	};

	function previousSlide() {
		
		transition(currentIndex, Math.max(currentIndex - 1, 0));
	};
	
	function updateView() {
		
		waitBox.show();
		
		Array.prototype.forEach.call(sheets, updateZoom);
		
		setTimeout(function() {
			sheets[currentIndex].show();
			waitBox.hide();
		}, 250);
	};
	
	function updateZoom(sheet) {

		sheet.style.display = "block";
	
		let _wrapper = sheet.childNodes.getByClassName("wrapper");
		_wrapper.style.transform = "scale(1)";
		_wrapper.style.width = "100%";
		
		let _zoom = _wrapper.offsetHeight / _wrapper.scrollHeight;
		
		if (_zoom < 1) {
			_wrapper.style.transform = "scale(" + _zoom + ")";
			_wrapper.style.width = (100 / _zoom) + "%";
		}
		
		sheet.style.display = "none";
		sheet.classList.add("trans-opacity");
	};
		
	function updateProgress() {
		
		progressDiv.style.width = (currentIndex / total) * 100 + "%";
	};
	
	function jumpToSlide(toIndex) {
		transition(currentIndex, toIndex);
	};

	function transition(fromIndex, toIndex) {
		
		let _current = sheets[fromIndex];
		let _next = sheets[toIndex];

		_current.style.zIndex = 1;
		_current.style.opacity = 0;
		_next.style.zIndex = 0;
		_next.style.display = "block";
		_next.style.opacity = 1;
		_current.addEventListener("transitionend", _current.afterHide, false);
		
		currentIndex = toIndex;
		
		updateProgress();
	};
	
	return {
		
		init:init,
		previousSlide:previousSlide,
		nextSlide:nextSlide,
		updateView:updateView,
		jumpToSlide:jumpToSlide
		
	};
	
})();

window.addEventListener('load', Prez.init, false);