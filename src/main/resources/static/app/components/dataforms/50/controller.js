angular
  .module('altairApp')
  .controller("dataFormsController50", ['$scope', 'mainService', '$stateParams', function($scope, mainService,$stateParams) {

    /*mainService.withdomain("post", "/getPlanListByForm/181").then(function(data) {
      console.log(data);
    });*/
	  
    $scope.proleGrid1 = {
      dataSource: {

        transport: {
          read: {
            url: "/user/angular/DataExcelGeorep4",
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
        }]
      },
      filterable: false,
      sortable: true,
      resizable: true,

      pageable: {
        refresh: true,
        pageSizes: true,
        buttonCount: 5
      },
      columns:[
 		{field:"data1", title: "Д/д"},
   		{field:"data2", title: "Дээж, сорьцын төрөл"},
   		{field:"data3", title: "Дээж, сорьцын тоо"},
   		{field:"data4", title: "Шинжилгээний төрөл"},
   		{field:"data5", title: "Тодорхойлсон элемент /нэгдэл, эрдэс/-ийн нэр"},
   		{field:"data6", title: "Шинжилгээ хийсэн лаборатори /Улс/"},
   		{field:"data7", title: "Дээжийн дубликат байгаа газар"}
   	],
      editable: false
    };

    if ($stateParams.param == null || $stateParams.param == undefined){
    	$scope.proleGrid1.toolbar = ["excel", "pdf"];
    	$scope.proleGrid1.excel = {fileName: "Report.xlsx",allPages: true};
    	$scope.proleGrid1.dataSource.group = {field: "annualRegistration.lpName",dir: "desc"}
    }

  }]);