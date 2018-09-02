angular
  .module('altairApp')
  .controller("dataFormsController29", ['$scope', 'mainService', function($scope, mainService) {
    $scope.domain = "com.peace.users.model.mram.DataMinPlan8";

    mainService.withdomain("post", "/getPlanListByForm/29").then(function(data) {
      console.log(data);
    });

    $scope.proleGrid1 = {
      dataSource: {

        transport: {
          read: {
            url: "/user/angular/DataMinPlan8",
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
          width: 75
        }, {
          field: "data2",
          title: "Марк",
          width: 150
        }, {
          field: "data3",
          title: "Загвар",
          width: 100
        }, {
          field: "data4",
          title: "Үйлдвэрлэгдсэн улс",
          width: 150
        }]
      }, {
        title: "Хүчин чадал багтаамж",
        columns: [{
          field: "data5",
          title: "Хэмжих нэгж",
          width: 100
        }, {
          field: "data6",
          title: "Хэмжээ",
          width: 100
        }]
      }, {
        title: "Ашиглалтын хугацаа / Цахилгаан зарцуулалт",
        columns: [{
            field: "data7",
            title: "Үйлдвэрлэсэн он",
            width: 120
          }, {
            field: "data8",
            title: "Ашиглалтанд өгсөн он",
            width: 120
          }, {
            field: "data9",
            title: "Ашиглалтын хугацаа",
            width: 120
          },{
              field: "data10",
              title: "Суурилагдсан чадал кВт (kW)",
              width: 120
            }, ]
      }, {
        title: "Түлш зарцуулалт",
        columns: [{
            field: "data11",
            title: "Хэмжих нэгж",
            width: 100
          }, {
            field: "data12",
            title: "Тоон утга (литр)",
            width: 90
          }, {
            field: "data13",
            title: "Нийт ажиллах гүйлт (мото/цаг)",
            width: 100
          }, {
              field: "data14",
              title: "Зарцуулах түлшний хэмжээ",
              width: 100
            }
        ]
      },
      {
          field: "data15",
          title: "Өмчлөлийн хэлбэр",
          width: 150
        },
        {
            field: "data16",
            title: "Тайлбар",
            width: 150
          },],
      editable: false
    };

  }]);