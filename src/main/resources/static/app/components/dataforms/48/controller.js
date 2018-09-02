angular
  .module('altairApp')
  .controller("dataFormsController48", ['$scope', 'mainService', '$stateParams', function($scope, mainService,$stateParams) {

    /*mainService.withdomain("post", "/getPlanListByForm/181").then(function(data) {
      console.log(data);
    });*/
	  
    $scope.proleGrid1 = {
      dataSource: {

        transport: {
          read: {
            url: "/user/angular/DataExcelGeorep2",
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
      groupable: true,
      pageable: {
        refresh: true,
        pageSizes: true,
        buttonCount: 5
      },
      columns:[
 		{field:"data1", title: "Д/д"},
 		{field:"data2", title: "#"},
   		{field:"data3", title: "Ажлын төрөл"},
   		{field:"data4", title: "Цалин"},
   		{field:"data5", title: "НДШимтгэл"},
   		{field:"data6", title: "Материалын зардал"},
   		{field:"data7", title: "Бичиг хэрэг"},
   		{field:"data8", title: "Холбоо"},
   		{field:"data9", title: "Томилолт"},
   		{field:"data10", title: "Ашиглалтын зардал"},
   		{field:"data11", title: "Түлш шатахуун"},
   		{field:"data12", title: "Үндсэн хөрөнгийн элэгдэл"},
   		{field:"data13", title: "Бусдаар гүйцэтгүүлсэн ажил"},
   		{title:"Тээвэр",columns:[{title:"Хүн", field:"data14"},{title:"Ачаа", field:"data15"}]},
   		{field:"data16", title: "Засварын зардал"},
   		{field:"data17", title: "БОНС-ын зардал"},
   		{field:"data18", title: "Бусад"},
   		{field:"data19", title: "Зардлын нийт дүн /мян.төг/"}
   	],
      editable: false
    };

    if ($stateParams.param == null || $stateParams.param == undefined){
    	$scope.proleGrid1.toolbar = ["excel", "pdf"];
    	$scope.proleGrid1.excel = {fileName: "Report.xlsx",allPages: true};
    	$scope.proleGrid1.dataSource.group = {field: "annualRegistration.lpName",dir: "desc"}
    }

  }]);