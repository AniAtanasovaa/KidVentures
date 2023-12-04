// Съхранете URL на изображението преди актуализацията
const imageUrl = document.querySelector('[data-place-id]').querySelector('img').src;

async function loadCommentsForPlace(placeId) {
    try {
        // Зареждане на коментарите
        const response = await fetch(`/comments/place/${placeId}`, {
            method: 'GET',
            headers: {
                'Accept': 'text/html',
            },
        });
        const data = await response.text();

        const commentsContainer = document.getElementById("comments-container");
        if (commentsContainer) {
            if (data.trim() !== "") {
                commentsContainer.innerHTML = data;
            } else {
                console.log("No comments available.");
            }
        } else {
            console.error("comments-container not found in the DOM");
        }

        // Възстановяване на изображението след актуализацията
        document.querySelector('[data-place-id]').querySelector('img').src = imageUrl;
    } catch (error) {
        console.error("Error loading comments:", error);
    }
}

document.querySelector("form").addEventListener("submit", async function (e) {
    e.preventDefault();
    const placeId = this.closest('[data-place-id]').getAttribute('data-place-id');
    console.log("Adding comment...");
    await fetch(`/comments/place/${placeId}`, {
        method: 'POST',
        headers: {
            'Accept': 'text/html',
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: new URLSearchParams(new FormData(this)).toString(),
    });
    console.log("Comment added. Loading comments...");
    loadCommentsForPlace(placeId);
});