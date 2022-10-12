const observer = new IntersectionObserver((entries) => {
    entries.forEach((entry) => {
        console.log(entry);
        if (entry.isIntersecting) {
            entry.target.classList.add("show");
        } else {
            entry.target.classList.remove("show");
        }
    });
});

const hiddenElements = document.querySelectorAll(".hidden");
hiddenElements.forEach((el) => observer.observe(el));

function showP() {
    document.getElementById("formContact").style.display = "none";
    document.getElementById("btnP").style.display = "none";
    document.getElementById("formFeedback").style.display = "block";
    document.getElementById("btnQ").style.display = "block";
}

function showQ() {
    document.getElementById("formFeedback").style.display = "none";
    document.getElementById("btnQ").style.display = "none";
    document.getElementById("formContact").style.display = "block";
    document.getElementById("btnP").style.display = "block";
}

