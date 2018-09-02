$.ajax({
    type: "GET",
    url: "/public/stats/1",
    dataType: 'json',
    success: function(data){
        var chart1 = Highcharts.chart('chart1', {

            chart: {
                type: 'column'
            },

            title: {
                text: ''
            },
            credits: {
                enabled: false
            },

            xAxis: {
                categories: ['Уул уурхайн үйлдвэрлэл, технологийн хэлтэс', 'Нүүрсний судалгааны хэлтэс', 'Геологи, хайгуулын хэлтэс']
            },

            yAxis: {
                allowDecimals: false,
                min: 0,
                title: {
                    text: 'Статистик үзүүлэлт'
                }
            },

            tooltip: {
                formatter: function () {
                    return '<b>' + this.x + '</b><br/>' +
                        this.series.name + ': ' + this.y + '<br/>' +
                        'Нийт: ' + this.point.stackTotal;
                }
            },

            plotOptions: {
                column: {
                    stacking: 'normal'
                }
            },

            series: data
        });
    }
});