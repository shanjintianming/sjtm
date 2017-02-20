(function($) {
	var PolaroidGallery = (function (element, options) {
		var self = this;
		$(element).addClass("polaroid-gallery");
	    if (options) {
	    	self.element = element;
	    	self._init(options);
		}
	});
	
	PolaroidGallery.prototype = {
		constructor: PolaroidGallery,
	    _init: function (options) {
	    	var self = this;
		    self.unordered = options.unordered;
		    var resizeTimeout = null;
		    var xmlhttp = new XMLHttpRequest();
		    var url = options.url;
		    
	    	// self._observe();
	        xmlhttp.onreadystatechange = function () {
	            if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
	                var myArr = JSON.parse(xmlhttp.responseText);
	                self._setGallery(myArr);
	                self._sort();
	                
	                if (self.unordered) {
	                	window.addEventListener('resize', function () {
		    	            if (resizeTimeout) {
		    	                clearTimeout(resizeTimeout);
		    	            }
		    	            resizeTimeout = setTimeout(function () {
		    	            	self._rsort();
		    	            }, 100);
		    	        });
	                }
	                
	                self._lightGallery();
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
	        	out += '<a href="' + arr[i].fileUrl + '" title="" rel="polaroid_1695986426" ';
	        	out += 'class="polaroid-gallery-item showcaption" style="z-index: 907; transform: rotate(5deg);">';
	        	out += '<span class="polaroid-gallery-image" title="" ';
	        	out += 'style="-webkit-background-size: 150px 150px;background-image:url(' + arr[i].fileUrl + ');';
	        	out += 'width:150px;height:150px"><img style="display:none!important" src="' + arr[i].fileUrl + '"></span></a>';
	        }
	        $(self.element).html(out);
	    },
	    _sort: function () {
	    	var self = this;
	    	var zIndex = 900,
			imagesCount = $('.polaroid-gallery a.polaroid-gallery-item').length,
			imageStr = (typeof(polaroid_gallery) !== 'undefined' ) ? polaroid_gallery.text2image : 'Image',
			thumbsOption = (typeof(polaroid_gallery) !== 'undefined' ) ? polaroid_gallery.thumbnail : 'none',
			imagesOption = (typeof(polaroid_gallery) !== 'undefined' ) ? polaroid_gallery.image : 'title3',
			scratches = (typeof(polaroid_gallery) !== 'undefined' ) ? polaroid_gallery.scratches : 'yes';
			var cssHoverObj = {
				'z-index' : '1001',
				'-webkit-transform' : 'scale(1.15)',
				'-moz-transform' :  'scale(1.15)',
				'-ms-transform' : 'scale(1.15)',
				'-o-transform' : 'scale(1.15)',
				'transform' : 'scale(1.15)'
				};
			var ua = self._uaMatch(navigator.userAgent);
			var isThisOldIe = (ua.browser == "msie" && ua.version*1 < 9);
			var getText;
			switch (thumbsOption) {			
					case 'image1':
						getText = function(currentIndex) { return imageStr +'&nbsp; '+ (currentIndex + 1); };
						break;
					case 'image2':
						getText = function(currentIndex) { return imageStr +'&nbsp; '+ (currentIndex + 1) +' / '+ imagesCount; };
						break;
					case 'number1':
						getText = function(currentIndex) { return (currentIndex + 1); };
						break;
					case 'number2':
						getText = function(currentIndex) { return (currentIndex + 1) +' / '+ imagesCount; }; 
						break;
					case 'none':
						getText = function(currentIndex) { return '&nbsp;'; };
						break;
					case 'caption':
					default:
						getText = function(currentIndex, defaultText) { return defaultText + '&nbsp;'; };
						break;
			};
		
	    	$(".polaroid-gallery a.polaroid-gallery-item").each(function(currentIndex) {
	    		zIndex++;
	    		var width = $(this).width(),
	    			text = jQuery.trim($("span", this).attr('title')),
	    			randNum = self._randomBetween(-12, 12),
	    			randDeg = 'rotate(' + randNum + 'deg)',
	    			randPos = self._randomBackgroundPosition(),
	    			ieFilter = self._ieRotateFilter(randNum);
		        
	    		text = getText(currentIndex, text);

	    		$("span", this).after('<span class="polaroid-gallery-text" style="width:'+width+'px;">'+text+'</span>');
	    		
	    		if (isThisOldIe) {
	    			var cssIeObj = {
	    				'filter' : ieFilter,
	    				'-ms-filter' : '"'+ ieFilter +'"'
	    			};
	    			$(this).css(cssIeObj);
	    		} else {			
	    			if(scratches === 'yes') {
	    				$("span.polaroid-gallery-text", this)
	    					.after('<span class="polaroid-gallery-scratches" style="background-position: '+randPos+';"></span>');
	    			}
	    		}
	    		
	    		var cssObj = {
	    			'z-index' : zIndex,
	    			'-webkit-transform' : randDeg,
	    			'-moz-transform' :  randDeg,
	    			'-ms-transform' : randDeg,
	    			'-o-transform' : randDeg,
	    			'transform' : randDeg
	    		};

	    		if (self.unordered) {
		    		$(this).css('position', 'absolute');
		    	} else {
		    		$(this).css('position', 'relative');
		    	}
	    		
	    		$(this).css(cssObj);
	    		$(this).hover(function () {
	    			$(this).css(cssHoverObj);
	    		}, function () {
	    			$(this).css(cssObj);
	    		});			
	    	});
	    	
	    	$(self.element).css('visibility', 'visible');
	    	
	    	if (self.unordered) {
	    		$(self.element).css('position', 'absolute');	    	
		    	self._rsort();
	    	} else {
	    		$(self.element).css('position', 'relative');
	    	}
	    },
	    // find a random number between 0 and int
		_random: function(X) {
			return Math.floor(X * (Math.random() % 1));
		},
		// find a random number between minValue and maxValue
		_randomBetween: function(MinV, MaxV) {
			var self = this;
			return MinV + self._random(MaxV - MinV + 1);
		},
		// random background-image position
		_randomBackgroundPosition: function() {
			var self = this;
			var top = ['top', 'center', 'bottom'],
				left = ['left', 'center', 'right'];
			return left[self._random(3)] + ' ' + top[self._random(3)];
		},
		// rotate filter for IE
		_ieRotateFilter: function(deg) {
			deg = (deg < 0) ? 360 + deg : deg;
			var deg2radians = Math.PI * 2 / 360,
				nAngle = deg * deg2radians,
				nCos = Math.cos(nAngle).toFixed(3),
				nSin = Math.sin(nAngle).toFixed(3);
			
			return "progid:DXImageTransform.Microsoft.Matrix(sizingMethod='auto expand', M11=" + nCos + ", M12=" + (-nSin) + ", M21=" + nSin + ", M22=" + nCos + ")";
		},
		// replacement for deprecated $.browser
		_uaMatch: function( ua ) {
		    ua = ua.toLowerCase();

		    var match = /(chrome)[ \/]([\w.]+)/.exec( ua ) ||
		        /(webkit)[ \/]([\w.]+)/.exec( ua ) ||
		        /(opera)(?:.*version|)[ \/]([\w.]+)/.exec( ua ) ||
		        /(msie) ([\w.]+)/.exec( ua ) ||
		        ua.indexOf("compatible") < 0 && /(mozilla)(?:.*? rv:([\w.]+)|)/.exec( ua ) ||
		        [];

		    return {
		        browser: match[ 1 ] || "",
		        version: match[ 2 ] || "0"
		    };
		},	 
	    //取得左右区域图片的取值活动范围:
	    _range: function () {
	    	var self = this;
	    	
	        //定义json数组格式:返回对象
	        var range = {
	            left: {
	                x: [], y: []
	            },
	            right: {
	                x: [], y: []
	            }
	        }
	        var wrap = {
	            w: $(self.element).width(),
	            h: $(self.element).height() - 150
	        }
	        var photo = {
	            w: $(".polaroid-gallery-item")[0].clientWidth,
	            h: $(".polaroid-gallery-item")[0].clientHeight
	        }
	        range.wrap = wrap;            //向json里面添加json元素.
	        range.photo = photo;
	 
	        //定义left的xy值范围 , 和right的xy范围值.
	        range.left.x = [0 - photo.w / 2, wrap.w / 2 - photo.w / 2];                     //定义json数组中的元素 , 用.方法.
	        range.left.y = [0 - photo.h / 2, wrap.h - photo.w / 2];
	 
	        range.right.x = [wrap.w / 2 - photo.w / 2, wrap.w - photo.w / 2];                     //定义json数组中的元素 , 用.方法.
	        range.right.y = [0 - photo.h / 2, wrap.h - photo.w / 2];
	 
	        return range;
	    },
	    
	    //随机选取一个作为中间的特殊元素,及两边排序.
	    _rsort:function () {
	    	
	    	var self = this;
	    	
	        //获取所有的photo , 准备排序
	        var _photo = $(".polaroid-gallery-item");
	        var photos = [];
	        for (var i = 0 ; i < _photo.length; i++) {
	            _photo[i].style.left = "";
	            _photo[i].style.top = ""; 
	            photos.push(_photo[i]);
	        }
	        
	        //定义变量存储数据值
	        var ranges = self._range();
	        var photos_left = photos.splice(0, Math.ceil(photos.length / 2));
	        var photos_right = photos;
	        
	        //通过随机的left和top值 , 随意排列图片:
	        for (var j = 0 ; j < photos_left.length; j++) {
	            photos_left[j].style.left = self._randomBetween(ranges.left.x[0],ranges.left.x[1]) + "px";
	            photos_left[j].style.top = self._randomBetween(ranges.left.y[0],ranges.left.y[1]) + "px";
	        }
	 
	        for (var s = 0 ; s < photos_right.length; s++) {
	            photos_right[s].style.left = self._randomBetween(ranges.right.x[0],ranges.right.x[1]) + "px";
	            photos_right[s].style.top = self._randomBetween(ranges.right.y[0],ranges.right.y[1]) + "px";
	        }
	    },
	    _lightGallery:function () {
	    	
	    	var self = this;
	    	
	        //获取所有的photo , 准备排序
	    	lightGallery(self.element[0],{

	            mode: 'lg-slide',

	            // Ex : 'ease'
	            cssEasing: 'ease',

	            //'for jquery animation'
	            easing: 'linear',
	            speed: 600,
	            height: '100%',
	            width: '100%',
	            addClass: '',
	            startClass: 'lg-start-zoom',
	            backdropDuration: 150,
	            hideBarsDelay: 6000,

	            useLeft: false,

	            closable: true,
	            loop: true,
	            escKey: true,
	            keyPress: true,
	            controls: true,
	            slideEndAnimatoin: true,
	            hideControlOnEnd: false,
	            mousewheel: false,

	            getCaptionFromTitleOrAlt: true,

	            // .lg-item || '.lg-sub-html'
	            appendSubHtmlTo: '.lg-sub-html',

	            subHtmlSelectorRelative: false,

	            preload: 1,
	            showAfterLoad: true,
	            selector: '',
	            selectWithin: '',
	            nextHtml: '',
	            prevHtml: '',

	            // 0, 1
	            index: false,

	            iframeMaxWidth: '100%',

	            download: true,
	            counter: true,
	            appendCounterTo: '.lg-toolbar',

	            swipeThreshold: 50,
	            enableSwipe: true,
	            enableDrag: true,

	            dynamic: false,
	            dynamicEl: [],
	            galleryId: 1
	        });
	    }
	};
	
	$.fn.polaroidGallery = function (option) {
        var opt = $.extend(true, $.fn.polaroidGallery.defaults, option);
        new PolaroidGallery(this, opt);
    };

	$.fn.polaroidGallery.defaults = {
	       url: '', // 加载路径
	       unordered: false //是否无序显示
	};
	
	$.fn.polaroidGallery.Constructor = PolaroidGallery;
})(window.jQuery || window.Zepto);
