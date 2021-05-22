function year_forth() {
    var year = document.getElementById("rm_year").getAttribute("value");
    var yearToShow = parseInt(year) + 1;
        window.location.href = "http://localhost:8090/road-map/" + yearToShow;
    }

function year_back() {
    var year = document.getElementById("rm_year").getAttribute("value");
    var yearToShow = parseInt(year) - 1;
        window.location.href = "http://localhost:8090/road-map/" + yearToShow;
    }
