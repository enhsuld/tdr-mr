angular
  .module('altairApp')
  .controller("dataFormsController51", ['$scope', 'mainService', '$stateParams', function($scope, mainService,$stateParams) {

    /*mainService.withdomain("post", "/getPlanListByForm/181").then(function(data) {
      console.log(data);
    });*/
	  
	var types = [{value:1, text:"1. Орд"},{value:2, text:"2. Илрэл"},{value:3, text:"3. Эрдэсжсэн цэг, цэгэн гажиг"},{value:4, text:"4. Элемент болон эрдэсийн сарнилын хүрээ"}];

    $scope.proleGrid1 = {
      dataSource: {

        transport: {
          read: {
            url: "/user/angular/DataExcelGeorep5",
            contentType: "application/json; charset=UTF-8",
            type: "POST",
            data:function(){
            	if ($stateParams.param != null && $stateParams.param != undefined && $stateParams.param > 0){
            		return {"custom":" where planid = " + $stateParams.param};
            	}
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
        pageSize: 1000,
        serverPaging: true,
        serverFiltering: true,
        scrollable: true,
        serverSorting: true,
        sort: [{
            field: "id",
            dir: "asc"
          },
          {
          field: "ordernumber",
          dir: "asc"
        }],
        group: {field: "type",dir: "asc"}
      },
      filterable: false,
      sortable: true,
      resizable: true,
      groupable: true,
      pageable: {
        refresh: true,
        pageSizes: true,
        buttonCount: 5
      },
      columns:[
 		{field:"data1", title: "Д/д"},
   		{field:"data2", title: "Нэр"},
   		{field:"data3", title: "АМ-н төрөл"},
   		{title:"Солбилцол",columns:[
   				{title:"Уртраг",columns:[{title:"Град", field:"data4"},{title:"Мин", field:"data5"},{title:"Сек", field:"data6"}]},
   				{title:"Өргөрөг",columns:[{title:"Град", field:"data7"},{title:"Мин", field:"data8"},{title:"Сек", field:"data9"}]},
   				{title:"Метр",columns:[{title:"X", field:"data10"},{title:"Y", field:"data11"}]},
   			],
   		},
   		{title:"Бүс, биеийн үзүүлэлт",columns:[{title:"Урт", field:"data12"},{title:"Өргөн", field:"data13"},{title:"Гүн", field:"data14"}]},
   		{field:"data15", title: "Дундаж агуулга"},
   		{field:"data16", title: "Урьдчилан үнэлсэн баялаг /нөөц/-ийн хэмжээ"},
   		{field:"type", hidden:true, title: "Төрөл", values:types}
   	],
      editable: false
    };

    if ($stateParams.param == null || $stateParams.param == undefined){
    	$scope.proleGrid1.toolbar = ["excel", "pdf"];
    	$scope.proleGrid1.excel = {fileName: "Report.xlsx",allPages: true};
    	$scope.proleGrid1.dataSource.group = {field: "annualRegistration.lpName",dir: "desc"}
    }

  }]);