const observer = new IntersectionObserver((entries) => {
    entries.forEach((entry) => {
        if (entry.isIntersecting && !entry.target.classList.contains("shown")) {
            entry.target.classList.add("show");
            entry.target.classList.add("shown");
        }
    });
});

const hiddenElements = document.querySelectorAll(".hidden");
hiddenElements.forEach((el) => observer.observe(el));

function showP() {
    if (document.getElementById("formFeedback").style.display === "none") {
        document.getElementById("formFeedback").style.display = "block";
    }
    else {
        document.getElementById("formFeedback").style.display = "none";
    }
}

function showQ() {
    if (document.getElementById("order_form").style.display === "none") {
        document.getElementById("order_form").style.display = "block";
    }
    else {
        document.getElementById("order_form").style.display = "none";
    }
}

window.onload = () => {
    const slider = new Slider(".quotes > .slider");

    document.querySelectorAll("form").forEach((el) => {
        el.addEventListener("submit", (e) => {
            e.preventDefault();
            sendForm(el);
        });
    });
}

function showVideo() {
    document.getElementById("video_click").classList.add("click_button_hide")
    document.getElementById("video").classList.add("video_show")
}


function showDropdown() {
    document.getElementById("myDropdown").classList.toggle("show_dropdown");
    changeBar();
}

window.onclick = function (event) {
    if (!event.target.matches('.dropbtn') && !event.target.matches('.dropdown_container')
        && !event.target.matches('.bar1') && !event.target.matches('.bar2') && !event.target.matches('.bar3')) {
        const dropdowns = document.getElementsByClassName("dropdown-content");
        let i;
        for (i = 0; i < dropdowns.length; i++) {
            const openDropdown = dropdowns[i];
            if (openDropdown.classList.contains('show_dropdown')) {
                openDropdown.classList.remove('show_dropdown');
            }
        }
        document.getElementById("dropdown_button").classList.remove("change")
    }
}

function changeBar() {
    document.getElementById("dropdown_button").classList.toggle("change");
}

class Slider {
    target;
    startShift = 0;
    endShift = 0;
    current = 0;
    size = 0;

    constructor(target) {
        target = document.querySelector(target);
        if (!(target instanceof Element)) throw new Error("Slider target must be a document element");
        this.target = target;
        this.size = this.target.querySelectorAll(".slider-container .slider-item").length;

        this.bindControls();
        this.appendPagination();
        this.bindHeight();
        this.updateHeight();
    }

    touchDown(e) {
        e.target.setPointerCapture(e.pointerId);
        this.startShift = e.screenX;
    }

    touchMove(e) {
        if ((e.target.hasPointerCapture && e.target.hasPointerCapture(e.pointerId))) {
            let shift = (e.screenX - this.startShift) / 3;
            if (Math.abs(shift) < 50) {
                this.target.querySelector(".slider-container .slider-item.active").style.transform = `translateX(${shift}px)`;
            } else {
                this.target.querySelector(".slider-container .slider-item.active").style.transform = `translateX(${shift > 0 ? "" : "-"}}50px)`;
            }
        }
    }

    touchUp(e) {
        this.endShift = e.screenX;

        this.target.querySelector(".slider-container .slider-item.active").style.transform = "translateX(0px)";
        if (Math.abs(this.startShift - e.screenX) > 20)
            this.startShift - e.screenX > 0 ? this.next() : this.previous();
        this.target.querySelector(".slider-container .slider-item.active").style.transform = "translateX(0px)";
    }

    next() {
        if (this.current >= this.size - 1) {
            this.switchTo(0);
        } else this.switchTo(this.current + 1);
    }

    previous() {
        if (this.current <= 0) {
            this.switchTo(this.size - 1);
        } else this.switchTo(this.current - 1);
    }

    switchTo(page) {
        this.target.querySelector(".slider-item.active").classList.remove("active");
        this.target.querySelector(`.slider-item[data-page="${page}"]`).classList.add("active");
        this.updateHeight();
        this.target.querySelector(".slider-button.active").classList.remove("active");
        this.target.querySelector(`.slider-button[data-page="${page}"]`).classList.add("active");

        if (this.current + 1 === page || this.current === this.size - 1 && page === 0)
            this.target.querySelector(`.slider-container .slider-item.active`).classList.add("slide-right");
        else
            this.target.querySelector(`.slider-container .slider-item.active`).classList.add("slide-left");

        let removeSlide = (e) => {
            e.target.removeEventListener("animationend", removeSlide);
            this.target.querySelectorAll(".slide-left, .slide-right").forEach((e) => e.classList.remove("slide-left", "slide-right"));
        }
        this.target.querySelector(".slider-item.active").addEventListener("animationend", removeSlide);

        this.current = page;
    }

    appendPagination() {
        this.target.querySelectorAll(".slider-buttons .slider-button").forEach((el, key) => {
            el.setAttribute("data-page", key);
        })
        this.target.querySelectorAll(".slider-container .slider-item").forEach((el, key) => {
            el.setAttribute("data-page", key);
        })
    }

    bindControls() {
        this.target.querySelector(".slider-container").addEventListener("pointerdown", (e) => this.touchDown(e), true);
        this.target.querySelector(".slider-container").addEventListener("pointermove", (e) => this.touchMove(e), true);
        this.target.querySelector(".slider-container").addEventListener("pointerup", (e) => this.touchUp(e), true);

        this.target.querySelectorAll(".slider-buttons .slider-button").forEach((el) => {
            el.addEventListener("click", (e) => {
                let page = +e.target.getAttribute("data-page");
                this.switchTo(page);
            })
        });
    }

    bindHeight() {
        window.addEventListener("resize", () => {
            this.updateHeight()
        });
    }

    updateHeight() {
        this.target.querySelector(".slider-container").style.height = this.target.querySelector(".slider-item.active").offsetHeight + "px";
    }
}

function sendForm(form) {
    if (!(form instanceof Element)) throw new Error("Form must be a document element");
    fetch(form.action, {
        method: form.method,
        body: new FormData(form)
    }).then((res) => {
        alert(res.ok ? "Успішно відправлено." : "Сталась помилка. Перевірте введені дані.")
        form.querySelector("button").disabled = false;
    });
    form.querySelector("button").disabled = true;
}