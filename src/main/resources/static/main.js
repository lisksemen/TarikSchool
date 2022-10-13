const observer = new IntersectionObserver((entries) => {
    entries.forEach((entry) => {
        console.log(entry);
        if (entry.isIntersecting && !entry.target.classList.contains("shown")) {
            entry.target.classList.add("show");
            entry.target.classList.add("shown");
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

window.onload = () => {
    const slider = new Slider(".quotes > .slider");
}

class Slider {
    target;
    startShift = 0;
    endShift = 0;
    current = 0;
    size = 0;

    constructor(target) {
        target = document.querySelector(target);
        if (!(target instanceof Element)) throw new Error("slider target must be a document element");
        this.target = target;
        this.size = this.target.querySelectorAll(".slider-container .slider-item").length;

        this.bindControls();
        this.appendPagination();
        this.bindHeight();
        target.querySelector(".slider-container").addEventListener("pointerdown", (e) => this.touchDown(e), true);
        target.querySelector(".slider-container").addEventListener("pointermove", (e) => this.touchMove(e), true);
        target.querySelector(".slider-container").addEventListener("pointerup", (e) => this.touchUp(e), true);
        window.addEventListener("resize", () => {this.bindHeight()});
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
                this.target.querySelector(".slider-container .slider-item.active").style.transform = `translateX(${shift>0?"":"-"}}50px)`;
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
        if (this.current >= this.target.querySelectorAll(".slider-container .slider-item").length-1){
            this.switchTo(0);
        } else this.switchTo(this.current + 1);
    }

    previous() {
        if (this.current <= 0) {
            this.switchTo(this.target.querySelectorAll(".slider-container .slider-item").length-1);
        } else this.switchTo(this.current - 1);
    }

    switchTo(page) {
        this.target.querySelector(".slider-item.active").classList.remove("active");
        this.target.querySelector(`.slider-item[data-page="${page}"]`).classList.add("active");
        this.bindHeight();
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
        this.target.querySelectorAll(".slider-buttons .slider-button").forEach((el) => {
            el.addEventListener("click", (e) => {
                let page = +e.target.getAttribute("data-page");
                this.switchTo(page);
            })
        });
    }

    bindHeight() {
        this.target.querySelector(".slider-container").style.height = this.target.querySelector(".slider-item.active").offsetHeight + "px";
    }
}