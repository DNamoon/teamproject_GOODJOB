/**
 * 남녀 성비
 */
var url = '/admin/genderStatistics';
$.getJSON(url, function (val) {
    var optionsVisitorsProfile = {

        series: [val.male, val.female],
        labels: ["남성", "여성"],
        dataLabels: {
            enabled: false
        },
        colors: ['#435ebe', '#55c6e8'],

        chart: {
            type: 'donut',
            width: '100%',
            height: '350px'
        },
        noData: {
            text: 'Loading...'
        },
        legend: {
            position: 'bottom'
        },
        plotOptions: {
            pie: {
                donut: {
                    size: '30%'
                }
            }
        }
    }
    var chart = new ApexCharts(document.querySelector("#chart"), optionsVisitorsProfile);
    chart.render();
});

/**
 * 최근 방문자 통계
 */
var options = {
    chart: {
        height: 350,
        type: 'bar',
    },
    dataLabels: {
        enabled: false
    },
    series: [],
    noData: {
        text: 'Loading...'
    },
    xaxis: {
        type: 'category',
        range: 7
    }
}

var chart2 = new ApexCharts(document.querySelector("#chart2"), options);
chart2.render();


var url = '/admin/visitorStatistics';
$.getJSON(url, function (response) {
    chart2.updateSeries([{
        name: 'visitor',
        data: response
    }])
});

/**
 * 채용현황
 */
var url = '/admin/postStatistics';
$.getJSON(url, function (val) {
    let title = '전체 채용 수 ' + (val.x + val.y)
    var optionsVisitorsProfile = {

        series: [val.x, val.y],
        labels: ["채용중", "채용마감"],
        title: {
            text: title,
            align: 'right'
        },
        dataLabels: {
            enabled: false
        },
        colors: ['#1822dc', '#b42525'],

        chart: {
            type: 'donut',
            width: '100%',
            height: '350px'
        },
        noData: {
            text: 'Loading...'
        },
        legend: {
            position: 'bottom'
        },
        plotOptions: {
            pie: {
                donut: {
                    size: '30%'
                }
            }
        }
    }
    var chart3 = new ApexCharts(document.querySelector("#chart3"), optionsVisitorsProfile);
    chart3.render();
});

/**
 * 총회원
 */
var url="/admin/totalMember"
$.getJSON(url, function (val) {
    $("#totalMember").text(val)
});

/**
 * 일일방문자수
 */
var url="/admin/dailyVisitor"
$.getJSON(url, function (val) {
    $("#dailyVisitor").text(val)
});

var url="/admin/totalVisitor"
$.getJSON(url, function (val) {
    $("#totalVisitor").text(val)
});

var url="/admin/customerInquiry/count"
$.getJSON(url, function (val) {
    $("#countByUnanswered").text(val)
});
