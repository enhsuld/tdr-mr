angular
  .module('altairApp')
  .controller("dataFormsController28", ['$scope', 'mainService', function($scope, mainService) {
    $scope.domain = "com.peace.users.model.mram.DataMinPlan7";

    mainService.withdomain("post", "/getPlanListByForm/28").then(function(data) {
      console.log(data);
    });

    $scope.proleGrid1 = {
      dataSource: {

        transport: {
          read: {
            url: "/user/angular/DataMinPlan7",
            contentType: "application/json; charset=UTF-8",
            type: "POST",
            data: {},
          },
          parameterMap: function(options) {
            return JSON.stringify(options);
          }
        },
        schema: {
          data: "data",
          total: "total",
          model: {
            id: "id",
          }
        },
        group: {
          field: "annualRegistration.lpName",
          dir: "desc"
        },
        pageSize: 1000,
        serverPaging: true,
        serverFiltering: true,
        scrollable: true,
        serverSorting: true,
        sort: {
          field: "id",
          dir: "asc"
        },

      },
      toolbar: ["excel", "pdf"],
      excel: {
        fileName: "Report.xlsx",
        allPages: true
      },
      filterable: false,
      sortable: true,
      resizable: true,
      groupable: true,
      pageable: {
        refresh: true,
        pageSizes: false,
        buttonCount: 5
      },
      columns: [{
        field: "id",
        hidden: true
      }, {
        field: "annualRegistration.lpName",
        title: "ААН",
        hidden: true,
        width: 200
      }, {
        field: "annualRegistration.licenseXB",
        title: "Тусгай зөвшөөрлийн дугаар",
        hidden: false,
        width: 150
      }, {
        title: "Тоног төхөөрөмжийн нэр",
        columns: [{
          field: "data_index",
          title: "#",
          width: 75
        }, {
          field: "data1",
          title: "Төрөл",
          width: 150
        }, {
          field: "data2",
          title: "Ангилал",
          width: 150
        }, {
          field: "data3",
          title: "Марк",
          width: 100
        }, {
          field: "data4",
          title: "Үйлдвэрлэгдсэн улс",
          width: 150
        }]
      }, {
        title: "Техникийн үзүүлэлт",
        columns: [{
          field: "data5",
          title: "Хэмжих нэгж",
          width: 100
        }, {
          field: "data6",
          title: "Хүчин чадал",
          width: 100
        },{
            field: "data7",
            title: "Тоо ширхэг",
            width: 120
          }]
      }, {
        title: "Тоног төхөөрөмжийн насжилт",
        columns: [{
            field: "data8",
            title: "Үйлдвэрлэсэн он",
            width: 120
          }, {
            field: "data9",
            title: "Ашиглалтанд өгсөн он",
            width: 120
          },{
              field: "data10",
              title: "Ашиглалтын хугацаа",
              width: 120
            }, ]
      }, {
        title: "Хөдөлгүүрийн чадал",
        columns: [{
            field: "data11",
            title: "Хэмжих нэгж",
            width: 100
          }, {
            field: "data12",
            title: "Тоон утга",
            width: 90
          }, {
            field: "data13",
            title: "Нийт чадал",
            width: 100
          }
        ]
      },
      {
          field: "data14",
          title: "Тайлбар",
          width: 150
        }],
      editable: false
    };

  }]);