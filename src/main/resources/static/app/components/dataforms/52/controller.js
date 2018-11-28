angular
  .module('altairApp')
  .controller("dataFormsController52", ['$scope', 'mainService', '$stateParams', function($scope, mainService,$stateParams) {

    /*mainService.withdomain("post", "/getPlanListByForm/181").then(function(data) {
      console.log(data);
    });*/
	  
    $scope.proleGrid1 = {
      dataSource: {

        transport: {
          read: {
            url: "/user/angular/DataExcelGeorep6",
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
   		{field:"data2", title: "Баримтын нэр, дугаар"},
   		{field:"data3", title: "Тоо ширхэг"},
   		{field:"data4", title: "Баримт дахь ажлын хэмжээ"},
   		{field:"data5", title: "Баримт бүрдүүлсэн геологчийн нэр"},
   		{field:"data6", title: "Баримт хадгалж буй газар"},
   		{field:"data7", title: "Тайлбар"}
   	],
      editable: false
    };

    if ($stateParams.param == null || $stateParams.param == undefined){
    	$scope.proleGrid1.toolbar = ["excel", "pdf"];
    	$scope.proleGrid1.excel = {fileName: "Report.xlsx",allPages: true};
    	$scope.proleGrid1.dataSource.group = {field: "annualRegistration.lpName",dir: "desc"}
    }

  }]);