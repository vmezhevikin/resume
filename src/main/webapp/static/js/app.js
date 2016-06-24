function deleteRow(row) {
	var ind = row.parentNode.parentNode.rowIndex;
	document.getElementById("table").deleteRow(ind);
}

var onloadCallback = function() {
	grecaptcha.render('g-recaptcha', {
		'sitekey' : '6Lfl2iETAAAAALdj_BDVgmo0HQdaG5c0kPGNeivL',
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
	},
	
	chooseFile : function() {
		$('#fileButton').parent().find('input[type=file]').click();
	},
	
	browseFile : function() {
		$('#fileInput').parent().parent().find('#fileName').html($('#fileInput').val().split(/[\\|/]/).pop());
	}
};