function deleteRow(r) {
	var i = r.parentNode.parentNode.rowIndex;
	document.getElementById("table").deleteRow(i);
}

var onloadCallback = function() {
	grecaptcha.render('g-recaptcha', {
		'sitekey' : 'sitekey',
		'callback' : function(response) {
			document.getElementById('recaptchaResponse').value = response;
		},
		'theme' : 'light'
	});
}

var resume = {

	alert : function(message) {
		alert(message);
	},

	welcomeMore : function() {
		var page = parseInt($('#profileContainer').attr('data-profile-number')) + 1;
		var total = parseInt($('#profileContainer').attr('data-profile-total'));
		if (page >= total) {
			$('#loadMoreIndicator').remove();
			$('#loadMoreContainer').remove();
			return;
		}
		var url = '/fragment/welcome-more?page=' + page;

		$('#loadMoreContainer').css('display', 'none');
		$('#loadMoreIndicator').css('display', 'block');
		$.ajax({
			url : url,
			success : function(data) {
				$('#loadMoreIndicator').css('display', 'none');
				$('#profileContainer').append(data);
				$('#profileContainer').attr('data-profile-number', page);
				if (page >= total - 1) {
					$('#loadMoreIndicator').remove();
					$('#loadMoreContainer').remove();
				} else {
					$('#loadMoreContainer').css('display', 'block');
				}
			},
			error : function(data) {
				$('#loadMoreIndicator').css('display', 'none');
				resume.alert('Error! Try again later...');
			}
		});
	},

	searchMore : function(query) {
		var page = parseInt($('#profileContainer').attr('data-profile-number')) + 1;
		var total = parseInt($('#profileContainer').attr('data-profile-total'));
		if (page >= total) {
			$('#loadMoreIndicator').remove();
			$('#loadMoreContainer').remove();
			return;
		}
		var url = '/fragment/search-more?query=' + query + '&page=' + page;

		$('#loadMoreContainer').css('display', 'none');
		$('#loadMoreIndicator').css('display', 'block');
		$.ajax({
			url : url,
			success : function(data) {
				$('#loadMoreIndicator').css('display', 'none');
				$('#profileContainer').append(data);
				$('#profileContainer').attr('data-profile-number', page);
				if (page >= total - 1) {
					$('#loadMoreIndicator').remove();
					$('#loadMoreContainer').remove();
				} else {
					$('#loadMoreContainer').css('display', 'block');
				}
			},
			error : function(data) {
				$('#loadMoreIndicator').css('display', 'none');
				resume.alert('Error! Try again later...');
			}
		});
	}
};