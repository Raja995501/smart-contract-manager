console.log("Ram Ram");
document.addEventListener("DOMContentLoaded", function() {
    document.querySelector("#contact-picture").addEventListener("change", function(event) {
        let file = event.target.files[0]; 
        if (file) {
            let reader = new FileReader();
            reader.onload = function(e) {
                document.querySelector("#upload_image_preview").setAttribute("src", e.target.result);
                document.querySelector("#upload_image_preview").style.display = 'block'; // Make sure the preview is visible
            };
            reader.readAsDataURL(file); // Read the file as a Data URL
        } else {
            document.querySelector("#upload_image_preview").style.display = 'none'; // Hide the preview if no file selected
        }
    });
});


