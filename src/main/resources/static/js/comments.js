// Функция за извличане и визуализация на коментарите за конкретното място
async function loadCommentsForPlace(placeId) {
    try {
        const response = await fetch(`/comments/${placeId}`, {
            method: 'GET',
            headers: {
                'Accept': 'text/html',
                // други хедъри, ако са необходими
            },
        });

        const data = await response.text();

        // Уверете се, че comments-container е вече наличен в DOM преди да го използвате
        const commentsContainer = document.getElementById("comments-container");
        if (commentsContainer) {
            commentsContainer.innerHTML = data;
        } else {
            console.error("comments-container not found in the DOM");
        }
    } catch (error) {
        console.error("Error loading comments:", error);
    }
}

window.onload = function () {
    // Обработчик на бутона за зареждане на коментарите
    document.getElementById('loadCommentsButton').addEventListener('click', function () {
        // Извличане на идентификационния номер от атрибута th:data
        const placeId = document.getElementById('loadCommentsButton').getAttribute('data-place-id');
        console.log("Loading comments...");
        // Извикване на функцията с динамично подаденото ID на място
        loadCommentsForPlace(placeId);
    });
};
