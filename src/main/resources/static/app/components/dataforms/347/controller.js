angular
  .module('altairApp')
  .controller("dataFormsController347", ['$scope', 'mainService', '$stateParams', function($scope, mainService,$stateParams) {

    /*mainService.withdomain("post", "/getPlanListByForm/181").then(function(data) {
      console.log(data);
    });*/
	  
	var types = [{value:1, text:"1. Суваг"},{value:2, text:"2. Шурф"},{value:3, text:"3. Цооног"},{value:4, text:"4. Бусад"}];

    $scope.proleGrid1 = {
      dataSource: {

        transport: {
          read: {
            url: "/user/angular/DataExcelGeorep7",
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
   		{field:"data2", title: "Уулын ажил /цооног/-ын дугаар"},
   		{title:"Байршил /Солбилцол/",columns:[
   				{title:"Уртраг",columns:[{title:"Град", field:"data3"},{title:"Мин", field:"data4"},{title:"Сек", field:"data5"}]},
   				{title:"Өргөрөг",columns:[{title:"Град", field:"data6"},{title:"Мин", field:"data7"},{title:"Сек", field:"data8"}]},
   				{title:"Метр",columns:[{title:"X", field:"data9"},{title:"Y", field:"data10"}]},
   			],
   		},
   		{field:"data11", title: "Ажлын хэмжээ"},
   		{field:"data12", title: "Хэмжих нэгж"},
   		{field:"data13", title: "Нээсэн огноо"},
   		{field:"data14", title: "Хаасан огноо"},
   		{field:"data15", title: "Нөхөн сэргээлтийн ажлын хэмжээ"},
   		{field:"data16", title: "Зардал /мян.төг/"},
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