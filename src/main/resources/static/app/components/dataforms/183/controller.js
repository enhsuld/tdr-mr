angular
  .module('altairApp')
  .controller(
    "dataFormsController183", [
      '$scope',
      'mainService',
      function($scope, mainService) {
        $scope.domain = "com.peace.users.model.mram.DataMinPlan4_1";
        /*$scope.types = [{text:"1. Олборлолт",value:1},{text:"2. Бүтээгдэхүүн гаргалт",value:2},{text:"3. Бүтээгдэхүүн борлуулалт",value:3},{text:"3.а Дотоод ",value:4},{text:"3.б Гадаад",value:5}];*/
        mainService.withdomain("post","/getPlanListByForm/183").then(function(data) {
        	$scope.proleGrid1 = {
        	          dataSource: {

        	            transport: {
        	              read: {
        	                url: "/user/angular/DataMinPlan4_1",
        	                contentType: "application/json; charset=UTF-8",
        	                type: "POST",
        	                data: {"custom":" where id in ("+data+")"},
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
        	            /*group: [{
        	                field: "annualRegistration.lpName",
        	                dir: "asc"
        	              },{
        	              field: "type",
        	              dir: "asc"
        	            }],*/
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
        	            hidden: false,
        	            width: 200
        	          }, {
        	            field: "annualRegistration.licenseXB",
        	            title: "Тусгай зөвшөөрлийн дугаар",
        	            hidden: false,
        	            width: 150
        	          }, {
        	            field: "data1",
        	            title: "Бүтээгдэхүүн",
        	            width: 150
        	          }, {
        	            title: "Бүтээгдэхүүний хэмжээ",
        	            columns:[
        	            	{
        	            		field: "data2",
        	                    title: "Х/нэгж",
        	                    width: 100
        	            	},
        	            	{
        	            		field: "data3",
        	                    title: "Тоон утга",
        	                    width: 100
        	            	}
        	            ]
        	          }, {
        	            field: "data4",
        	            title: "#",
        	            width: 50
        	          },{
        	              field: "data5",
        	              title: "Ашигт малтмалын нэр",
        	              width: 100
        	            }, {
        	            title: "Агуулга",
        	            columns: [{
        	              field: "data6",
        	              title: "Х/нэгж",
        	              width: 100
        	            }, {
        	              field: "data7",
        	              title: "Тоон утга",
        	              width: 100
        	            }]
        	          }, {
        	            title: "Метал (эрдэс) хэмжээ",
        	            columns: [{
        	              field: "data8",
        	              title: "Х/нэгж",
        	              width: 100
        	            }, {
        	              field: "data9",
        	              title: "Тоон утга",
        	              width: 100
        	            }]
        	          },
        	          {
        	              title: "Гарц",
        	              columns: [{
        	                field: "data10",
        	                title: "Х/нэгж",
        	                width: 100
        	              }, {
        	                field: "data11",
        	                title: "Тоон утга",
        	                width: 100
        	              }]
        	            },
        	            {
        	                title: "Эрдэс авалт",
        	                columns: [{
        	                  field: "data12",
        	                  title: "Х/нэгж",
        	                  width: 100
        	                }, {
        	                  field: "data13",
        	                  title: "Тоон утга",
        	                  width: 100
        	                }]
        	              },{
        	                  field: "data14",
        	                  title: "Тайлбар",
        	                  width: 120
        	                }],
        	          editable: false
        	        };
          });

      }
    ]);