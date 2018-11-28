angular
  .module('altairApp')
  .controller(
    "dataFormsController31", [
      '$scope',
      'mainService',
      function($scope, mainService) {
        $scope.domain = "com.peace.users.model.mram.DataMinPlan10";
        $scope.types = [{
          text: "Техникийн нөхөн сэргээлт - Гадаад овоолго",
          value: 1
        }, {
          text: "Техникийн нөхөн сэргээлт - Дотоод овоолго",
          value: 2
        }, {
          text: "Биологийн нөхөн сэргээлт - Шимт хөрс",
          value: 3
        }, {
          text: "Биологийн нөхөн сэргээлт - Биологи ",
          value: 4
        }, {
          text: "Дүйцүүлэн хамгаалах нөхөн сэргээлт",
          value: 5
        }, {
          text: "Уурхайг тохижуулах, тосгон орчим хийгдэх нөхөн сэргээлт",
          value: 6
        },
        {
            text: "Нийт",
            value: 7
          }];
        mainService.withdomain("post", "/getPlanListByForm/31").then(function(data) {
          console.log(data);
        });

        $scope.proleGrid1 = {
          dataSource: {

            transport: {
              read: {
                url: "/user/angular/DataMinPlan10",
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
            group: [{
                field: "annualRegistration.lpName",
                dir: "asc"
              }
              /*,{
                            field: "type",
                            dir: "asc"
                          }*/
            ],
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
          columns: [{
            field: "id",
            hidden: true
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
            field: "type",
            title: "Үзүүлэлт",
            width: 200,
            values: $scope.types
          }, {
            title: "Талбай",
            columns: [{
              field: "data1",
              title: "х/нэгж",
              width: 100
            }, {
              field: "data2",
              title: "Тоон утга",
              width: 100
            }]
          }, {
            title: "Эзэлхүүн",
            columns: [{
              field: "data3",
              title: "х/нэгж",
              width: 100
            }, {
              field: "data4",
              title: "Тоон утга",
              width: 100
            }]
          }, {
            title: "Зардлын хэмжээ",
            columns: [{
              field: "data5",
              title: "х/нэгж",
              width: 100
            }, {
              field: "data6",
              title: "Тоон утга",
              width: 100
            }]
          }, {
            field: "data7",
            title: "Тайлбар",
            width: 150
          }, {
            title: "Солбицол",
            columns: [{
                title: "Уртраг",

                columns: [{
                    title: "Град",
                    field: "data8",
                    width: 75
                  }, {
                    title: "Мин",
                    field: "data9",
                    width: 75
                  }, {
                    title: "Сек",
                    field: "data10",
                    width: 75
                  }

                ]
              }, {
                title: "Өргөрөг",

                columns: [{
                    title: "Град",
                    field: "data11",
                    width: 75
                  }, {
                    title: "Мин",
                    field: "data12",
                    width: 75
                  }, {
                    title: "Сек",
                    field: "data13",
                    width: 75
                  }

                ]
              }, {
                title: "Метрийн",

                columns: [{
                    title: "X",
                    field: "data14",
                    width: 75
                  }, {
                    title: "Y",
                    field: "data15",
                    width: 75
                  }

                ]
              }

            ]
          }],
          editable: false
        };

      }
    ]);