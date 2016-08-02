;$(function() {
	
	var newIndex, blankItem;
	
	var init = function() {
		$('#loadMoreBtn').click(loadMoreProfiles);
		$('#fileButton').click(openChooseFileWindow);
		$('#fileInput').change(setChosenFileName);
		$('.modal-certificate-btn').click(showCertificateModal);
		showMessageModal();
		$('#add-item').click(addItem);
		initEditItem();
		initRemoveBtns();
		$('.hobby-btn').click(switchHobbyStatus);
	};


	var showAlertMessage = function(message) {
		alert(message);
	};


	var setItemIndex = function(item, oldIndex, newIndex) {
		$('#item-' + oldIndex + ' [name]').each(function() {
			var input = $(this);
			var oldValue = input.attr('name');
			var newValue = oldValue.replace('items[' + oldIndex + '].', 'items[' + newIndex + '].');
			input.attr('name', newValue);
		});
		$('#item-' + oldIndex).attr('id', 'item-' + newIndex);
		$('#close-btn-' + oldIndex).attr('data-item', newIndex);
		$('#close-btn-' + oldIndex).attr('id', 'close-btn-' + newIndex);
	};
	var shiftItems = function() {
		$('.remove-item-btn').each(function(index) {
			var closeBtn = $(this);
			var oldIndex = parseInt(closeBtn.attr('data-item'));
			var item = $('#item-' + oldIndex);
			setItemIndex(item, oldIndex, index);
		});
		initRemoveBtns();
	};
	var removeItem = function() {
		var btn = $(this);
		var index = parseInt(btn.attr('data-item'));
		$('#item-' + index).remove();
		shiftItems();
		newIndex--;
	};
	var addItem = function() {
		var item = blankItem.clone();
		item.insertBefore($('#add-item-row'));
		setItemIndex(item, 'blank', newIndex);
		newIndex++;
		initRemoveBtns();
	};
	var initEditItem = function() {
		newIndex = parseInt($('#edit-table').attr('data-init-size'));
		blankItem = $('#item-' + newIndex);
		setItemIndex(blankItem, newIndex, 'blank');
		blankItem.remove();
	};
	var initRemoveBtns = function() {
		$('.remove-item-btn').off('click', removeItem);
		$('.remove-item-btn').click(removeItem);
	};


	var loadMoreProfiles = function() {
		var page = parseInt($('#profileContainer').attr('data-profile-number')) + 1;
		var total = parseInt($('#profileContainer').attr('data-profile-total'));
		if (page >= total) {
			$('#loadMoreIndicator').remove();
			$('#loadMoreContainer').remove();
			return;
		};
		var url = '/more-profiles?page=' + page;
		var query = $('#profileContainer').attr('data-search-query');
		if (query !== '') {
			url += '&query=' + query;
		};

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
				showAlertMessage('Error! Try again later...');
			}
		});
	};


	var openChooseFileWindow = function() {
		$('#fileInput').click();
	};
	var setChosenFileName = function() {
		var chosenFileName = $('#fileInput').val().split(/[\\|/]/).pop();
		$('#fileName').html(chosenFileName);
	};


	var showCertificateModal = function() {
		var btn = $(this);
		var certificateImage = btn.attr('data-certificate-img');
		var certificateDescription = btn.attr('data-certificate-desc');
		$('#modal-certificate-title').text(certificateDescription);
		$('#modal-certificate-img').attr('src', certificateImage);
	};
	var showMessageModal = function() {
		var message = $('#modal-message').attr('data-message');
		if (message !== '') {
			$('#modal-message').modal('show');
			setTimeout(function() {
				$('#modal-message').modal('hide');
			}, 3000);
		};
	};


	var switchHobbyStatus = function() {
		var btn = $(this);
		var index = parseInt(btn.attr('data-hobby-ind'));
		var input = $('#hobby-input-' + index);
		var status = input.val();
		var countInput = $('#current-hobby-count');
		var count = parseInt(countInput.attr('value'));
		if (status === 'checked') {
			input.val('unchecked');
			btn.removeClass('btn-success');
			btn.addClass('btn-default');
			countInput.attr('value', count - 1);
		};
		if (status === 'unchecked') {
			input.val('checked');
			btn.removeClass('btn-default');
			btn.addClass('btn-success');
			countInput.attr('value', count + 1);
		};
	};
	

	init();
});

var onloadCallback = function() {
	var sitekey = $('#g-recaptcha').attr('data-sitekey');
	grecaptcha.render('g-recaptcha', {
		sitekey : sitekey,
		callback : function(response) {
			$('#recaptchaResponse').val(response);
		},
		theme : 'light'
	});
};