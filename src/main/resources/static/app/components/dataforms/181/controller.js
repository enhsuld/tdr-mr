angular
  .module('altairApp')
  .controller("dataFormsController182", ['$scope', 'mainService', function($scope, mainService) {
    $scope.domain = "com.peace.users.model.mram.DataMinPlan2_2";

    mainService.withdomain("post", "/getPlanListByForm/181").then(function(data) {
      console.log(data);
    });

    $scope.proleGrid1 = {
      dataSource: {

        transport: {
          read: {
            url: "/user/angular/DataMinPlan2_2",
            contentType: "application/json; charset=UTF-8",
            type: "POST",
            data: {
              "custom": "where type in (1,2)"
            },
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

      pageable: {
        refresh: true,
        pageSizes: false,
        buttonCount: 5
      },
      /*detailInit: detailInit,
      dataBound: function() {
          this.expandRow(this.tbody.find("tr.k-master-row").first());
      },*/
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
        title: "Ерөнхий мэдээлэл",
        columns: [{
          field: "dataIndex",
          title: "#",
          width: 75
        }, {
          field: "data1",
          title: "Хүдрийн биет",
          width: 200
        }, {
          field: "data2",
          title: "Геологийн нөөцийн ангилал",
          width: 100
        }, {
          field: "data3",
          title: "Хүдэр (элс)",
          width: 200
        }, {
          field: "data4",
          title: "Блокийн дугаар",
          width: 200
        }]
      }, {
        title: "Геологийн нөөц",
        columns: [{
          field: "data5",
          title: "Блокийн талбай /м2/",
          width: 100
        }, {
          field: "data6",
          title: "Хөрсний зузаан /м/",
          width: 100
        }, {
          field: "data7",
          title: "Давхаргын зузаан /м/",
          width: 100
        }, {
          field: "data8",
          title: "Хөрсний эзэлхүүн /мян.м3/",
          width: 100
        }, {
          field: "data9",
          title: "Хүдрийн (элс) эзэлхүүн /мян.м3/",
          width: 100
        }, {
          field: "data10",
          title: "Хүдрийн (элс) эзэлхүүн жин /тн.м3/",
          width: 100
        }, {
          title: "Хүдрийн (элс) хэмжээ",
          columns: [{
            field: "data11",
            title: "Хэмжих нэгж",
            width: 90
          }, {
            field: "data12",
            title: "Тоон утга",
            width: 75
          }, ]
        }]
      }, {
        title: "Ашиглалтын хаягдал",
        columns: [{
          title: "Хаягдал хүдэр (элс)",
          columns: [{
            field: "data13",
            title: "Хаягдлын хувь /%/",
            width: 100
          }, {
            field: "data14",
            title: "Хэмжих нэгж",
            width: 90
          }, {
            field: "data15",
            title: "Тоон утга",
            width: 75
          }, ]
        }]
      }, {
        title: "Бохирдолт",
        columns: [{
          title: "Бохирдуулагч чулуулаг",
          columns: [{
            field: "data16",
            title: "Бохирдолтын хувь /%/",
            width: 100
          }, {
            field: "data17",
            title: "Хэмжих нэгж",
            width: 90
          }, {
            field: "data18",
            title: "Тоон утга",
            width: 75
          }, ]
        }]
      }, {
        title: "Үйлдвэрлэлийн нөөц",
        columns: [{
          title: "Нэмэгдэх хөрсний нөөц",
          columns: [{
            field: "data19",
            title: "Хэмжих нэгж",
            width: 90
          }, {
            field: "data20",
            title: "Тоон утга",
            width: 75
          }, ]
        }, {
          title: "Хөрс хуулалт",
          columns: [{
            field: "data21",
            title: "Хэмжих нэгж",
            width: 90
          }, {
            field: "data22",
            title: "Тоон утга",
            width: 75
          }, ]
        }, {
          title: "Хүдрийн (элс) нөөц",
          columns: [{
            field: "data23",
            title: "Хэмжих нэгж",
            width: 90
          }, {
            field: "data24",
            title: "Тоон утга",
            width: 75
          }, ]
        }]
      }],
      editable: false
    };



  }]);