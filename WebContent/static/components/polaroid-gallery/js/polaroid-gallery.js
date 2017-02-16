(function($) {
	var PolaroidGallery = (function (element, options) {
		var self = this;
	    if (options) {
	    	self.element = element;
	    	self._init(options);
		}
	});
	
	PolaroidGallery.prototype = {
		constructor: PolaroidGallery,
	    _init: function (options) {
	    	var self = this;
			self.dataSize = {};
		    self.dataLength = 0;
		    self.currentItem = null;
		    self.navbarHeight = 60;
		    var resizeTimeout = null;
		    var xmlhttp = new XMLHttpRequest();
		    var url = options.url;
		    
	    	self._observe();
	        xmlhttp.onreadystatechange = function () {
	            if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
	                var myArr = JSON.parse(xmlhttp.responseText);
	                self._setGallery(myArr);
	                
	                window.addEventListener('resize', function () {
	    	            if (resizeTimeout) {
	    	                clearTimeout(resizeTimeout);
	    	            }
	    	            resizeTimeout = setTimeout(function () {
	    	            	self._shuffleAll();
	    	                if (self.currentItem) {
	    	                	self._select(self.currentItem);
	    	                }
	    	            }, 100);
	    	        });
	            }
	        };
	        xmlhttp.open("GET", url, true);
	        xmlhttp.send();
	    },
	    _setGallery: function (arr) {
	    	var self = this;
	        var out = "";
	        var i;
	        for (i = 0; i < arr.length; i++) {
	            out += '<figure id="' + i + '">' +
	                '<img src="' + arr[i].fileUrl + '" />' +
	                '<figcaption>' + i + '</figcaption>' +
	                '<div style="display:none" class="control-wrapper">' +  
	                '<a class="left carousel-control" href="#fullCarousel" data-slide="prev">' +  
	                '<span class="glyphicon glyphicon-chevron-left"></span>' +  
	                '</a>' +  
	                '<a class="right carousel-control" href="#fullCarousel" data-slide="next">' +  
	                '<span class="glyphicon glyphicon-chevron-right"></span>' +  
	                '</a>' +  
	                '</div>' +
	                '</figure>';
	        }
	        $(self.element).html(out);
	    },
	    _observe: function () {
	    	var self = this;
	        var observeDOM = (function () {
	            var MutationObserver = window.MutationObserver || window.WebKitMutationObserver,
	                eventListenerSupported = window.addEventListener;

	            return function (obj, callback) {
	                if (MutationObserver) {
	                    var obs = new MutationObserver(function (mutations, observer) {
	                        if( mutations[0].addedNodes.length || mutations[0].removedNodes.length )
	                        callback(mutations);
	                    });

	                    obs.observe(obj, { childList: true, subtree: false });
	                }
	                else if (eventListenerSupported) {
	                    obj.addEventListener('DOMNodeInserted', callback, false);
	                }
	            }
	        })();

	        observeDOM(self.element[0], function (mutations) {
	            var gallery = [].slice.call(mutations[0].addedNodes);
	            var zIndex = 1;
	            gallery.forEach(function (item) {
	                var img = item.getElementsByTagName('img')[0];
	                var first = true;
	                img.addEventListener('load', function () {
	                    if (first) {
	                    	self.currentItem = item;
	                        first = false;
	                    }
	                    self.dataSize[item.id] = {item: item, width: item.offsetWidth, height: item.offsetHeight};

	                    self.dataLength++;

	                    item.addEventListener('click', function (e) {
	                    	e = e || window.event;
	                        var tar = e.target || e.srcElement;
	                        
	                        if (tar.tagName.toLowerCase() != "a" 
	                        	&& tar.tagName.toLowerCase() != "span") {
	                        	self._select(item);
		                    	self._shuffleAll();
	                        }
	                    });

	                    self._shuffle(gallery.length, item, zIndex++);
	                })
	            });
	        });
	    },
	    _select: function (item) {
	    	var self = this;
	        var scale = 1.8;
	        var rotRandomD = 0;

	        var initWidth = self.dataSize[item.id].width;
	        var initHeight = self.dataSize[item.id].height;

	        var newWidth = (initWidth * scale);
	        var newHeight = initHeight * (newWidth / initWidth);

	        var x = (self.element.width() - newWidth) / 2;
	        var y = (window.innerHeight - self.navbarHeight - newHeight) / 2;

	        item.style.transformOrigin = '0 0';
	        item.style.WebkitTransform = 'translate(' + x + 'px,' + y + 'px) rotate(' + rotRandomD + 'deg) scale(' + scale + ',' + scale + ')';
	        item.style.msTransform = 'translate(' + x + 'px,' + y + 'px) rotate(' + rotRandomD + 'deg) scale(' + scale + ',' + scale + ')';
	        item.style.transform = 'translate(' + x + 'px,' + y + 'px) rotate(' + rotRandomD + 'deg) scale(' + scale + ',' + scale + ')';
	        item.style.zIndex = 999;

	        self._navigation(item.getElementsByTagName("div")[0]);
	        
	        item.onmouseenter = function(){
	        	var _div = item.getElementsByTagName("div")[0];
	        	self._navigation(_div);
	        }
	        
	        item.onmouseleave = function(){
	        	var _div = item.getElementsByTagName("div")[0];
	        	_div.style.display="none";
	        	
	        	_preview = _div.getElementsByTagName("a")[0];
	        	_next = _div.getElementsByTagName("a")[1];
	        	item.getElementsByTagName("div")[0].style.display="none";
	        	_preview.onclick = null;
	        	_next.onclick = null;
	        }
	        
	        self.currentItem = item;
	    },

	    _shuffle: function (length, item, zIndex) {
	    	var self = this;
	    	var innerWidth = self.element.width();
	    	
	        var randomX = Math.random();
	        var randomY = Math.random();
	        var maxR = 45;
	        var minR = -45;
	        var rotRandomD = Math.random() * (maxR - minR) + minR;
	        var rotRandomR = rotRandomD * Math.PI / 180;

	        var rotatedW = Math.abs(item.offsetWidth * Math.cos(rotRandomR)) + Math.abs(item.offsetHeight * Math.sin(rotRandomR));
	        var rotatedH = Math.abs(item.offsetWidth * Math.sin(rotRandomR)) + Math.abs(item.offsetHeight * Math.cos(rotRandomR));

	        var x = Math.floor((innerWidth - rotatedW) * randomX);
	        var y = Math.floor((window.innerHeight - rotatedH) * randomY);
	        
	        item.style.transformOrigin = '0 0';
	        item.style.WebkitTransform = 'translate(' + x + 'px,' + y + 'px) rotate(' + rotRandomD + 'deg) scale(1)';
	        item.style.msTransform = 'translate(' + x + 'px,' + y + 'px) rotate(' + rotRandomD + 'deg) scale(1)';
	        item.style.transform = 'translate(' + x + 'px,' + y + 'px) rotate(' + rotRandomD + 'deg) scale(1)';
	        item.style.zIndex = zIndex;
	        
	        item.onmouseenter = null;
	        item.onmouseleave = null;
	    },

	    _shuffleAll: function () {
	    	var self = this;
	        var zIndex = 0;
	        for (var id in self.dataSize) {
	            if (id != self.currentItem.id) {
	            	self._shuffle(self.dataSize.length, self.dataSize[id].item, zIndex++);
	            }
	        }
	    },
	    _navigation: function (_div) {
	    	var self = this; 
	    	_div.style.display="inline";
        	
        	_preview = _div.getElementsByTagName("a")[0];
        	_next = _div.getElementsByTagName("a")[1];
        	
        	_preview.onclick = null;
        	_next.onclick = null;
        	
        	_preview.onclick = function () {
        		self.currentItem.getElementsByTagName("div")[0].style.display="none";
        		var currentIndex = Number(self.currentItem.id) - 1;
                if (currentIndex < 0) {
                    currentIndex = self.dataLength - 1
                }
                self._select(self.dataSize[currentIndex].item);
                self._shuffleAll();
                self.currentItem.getElementsByTagName("div")[0].style.display="inline";
        	}
        	
        	_next.onclick = function next() {
        		self.currentItem.getElementsByTagName("div")[0].style.display="none";
        		var currentIndex = Number(self.currentItem.id) + 1;
                if (currentIndex >= self.dataLength) {
                    currentIndex = 0
                }
                self._select(self.dataSize[currentIndex].item);
                self._shuffleAll();
                self.currentItem.getElementsByTagName("div")[0].style.display="inline";
        	}
	    }
	};
	
	$.fn.polaroidGallery = function (option) {
        var opt = $.extend(true, $.fn.polaroidGallery.defaults, option);
        new PolaroidGallery(this, opt);
    };

	$.fn.polaroidGallery.defaults = {
	       url: '' // 语言
	};
	
	$.fn.polaroidGallery.Constructor = PolaroidGallery;
})(window.jQuery || window.Zepto);
