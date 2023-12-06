// Променете функцията loadCommentsForPlace да използва jQuery
async function loadCommentsForPlace(placeId) {
    $.ajax({
        type: 'GET',
        url: '/comments/place/' + placeId,
        success: function(data) {
            $('#comments-container').html(data);
        },
        error: function(error) {
            console.error('Error loading comments:', error);
        }
    });
}

// Обработчик за бутона за зареждане на коментарите
$('#load-comments-btn').on('click', function () {
    const placeId = $(this).data('place-id');
    console.log("Loading comments for place ID:", placeId);
    loadCommentsForPlace(placeId);
});

// Изпълнение на заявката при зареждане на страницата
$(document).ready(function() {
    const placeId = document.querySelector('[data-place-id]').getAttribute('data-place-id');
    loadCommentsForPlace(placeId);
});

// Добавете този ред за съхранение на URL на изображението в #comments-container
$('#comments-container').data('image-url', $('[data-place-id]').find('img').attr('src'));

// Изпълнение на заявката при добавяне на коментар
$('form').submit(async function(e) {
    e.preventDefault();
    const placeId = this.closest('[data-place-id]').getAttribute('data-place-id');
    console.log('Adding comment...');
    await fetch('/comments/place/' + placeId, {
        method: 'POST',
        headers: {
            'Accept': 'text/html',
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: new URLSearchParams(new FormData(this)).toString(),
    });

    // След като добавим коментар, зареждаме отново коментарите
    console.log('Comment added. Loading comments...');
    loadCommentsForPlace(placeId);
});

// Обработчик за бутона за добавяне на коментар
$('#add-comment-btn').on('click', function () {
    // Покажете формата за добавяне на коментар
    $('form').show();
});