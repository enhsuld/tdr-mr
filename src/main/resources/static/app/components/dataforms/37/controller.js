angular
  .module('altairApp')
  .controller(
    "dataFormsController37", [
      '$scope',
      'mainService',
      function($scope, mainService) {
        $scope.domain = "com.peace.users.model.mram.DataMinPlan3";
        $scope.types = [{text:"1. Олборлолт",value:1},{text:"2. Бүтээгдэхүүн гаргалт",value:2},{text:"3. Бүтээгдэхүүн борлуулалт",value:3},{text:"3.а Дотоод ",value:4},{text:"3.б Гадаад",value:5}];
        mainService.withdomain("post","/getPlanListByForm/37").then(function(data) {
            console.log(data);
          });

        $scope.proleGrid1 = {
          dataSource: {

            transport: {
              read: {
                url: "/user/angular/DataMinPlan3",
                contentType: "application/json; charset=UTF-8",
                type: "POST",
                data: {"custom":"where noteid = 37"},
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
            group: [{
                field: "annualRegistration.lpName",
                dir: "asc"
              },{
              field: "type",
              dir: "asc"
            }],
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
          },{
        	  field: "type",
        	  hidden:true,
        	  values:$scope.types
          }, {
            field: "annualRegistration.lpName",
            title: "ААН",
            hidden: false,
            width: 200
          }, {
            field: "annualRegistration.licenseXB",
            title: "Тусгай зөвшөөрлийн дугаар",
            hidden: false,
            width: 150
          }, {
            field: "data1",
            title: "Үзүүлэлт",
            width: 150
          }, {
            field: "data2",
            title: "Хэмжих нэгж",
            width: 150
          }, {
            field: "data3",
            title: "Жилийн төлөвлөгөө",
            width: 150
          }, {
            title: "Сарууд",
            columns: [{
              field: "data4",
              title: "1",
              width: 75
            }, {
              field: "data5",
              title: "2",
              width: 75
            }, {
              field: "data6",
              title: "3",
              width: 75
            }, {
              field: "data7",
              title: "4",
              width: 75
            }, {
              field: "data8",
              title: "5",
              width: 75
            }, {
              field: "data9",
              title: "6",
              width: 75
            }, {
              field: "data10",
              title: "7",
              width: 75
            }, {
              field: "data11",
              title: "8",
              width: 75
            }, {
              field: "data12",
              title: "9",
              width: 75
            }, {
              field: "data13",
              title: "10",
              width: 75
            }, {
              field: "data14",
              title: "11",
              width: 75
            }, {
              field: "data15",
              title: "12",
              width: 75
            }]
          }, {
            title: "Улирлууд",
            columns: [{
              field: "data16",
              title: "I",
              width: 75
            }, {
              field: "data17",
              title: "II",
              width: 75
            }, {
              field: "data18",
              title: "III",
              width: 75
            }, {
              field: "data19",
              title: "IV",
              width: 75
            }]
          }],
          editable: false
        };

      }
    ]);