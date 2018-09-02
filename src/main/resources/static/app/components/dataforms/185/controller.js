angular
  .module('altairApp')
  .controller("dataFormsController185", ['$scope', 'mainService', function($scope, mainService) {
    $scope.domain = "com.peace.users.model.mram.DataMinPlan1";

    mainService.withdomain("post", "/getPlanListByForm/185").then(function(dt) {
      console.log(dt);
      
      var concentration=[{"text":"Ангилан ялгах","value":1},{"text":"Бэлтгэл ажил","value":2},{"text":"Гравитаци","value":3},{"text":"Флотаци","value":4},{"text":"Гар ялгалт","value":5},{"text":"Нойтон орчинд соронзонгоор ангилах","value":6},{"text":"Хуурай орчинд соронзонгоор ангилах","value":7},{"text":"Угаах","value":8},{"text":"Уусгах","value":9},{"text":"Нуруулдан уусгах","value":10},{"text":"Овоолгон уусгах","value":11},{"text":"Газар доор уусгах","value":12},{"text":"Шатаах","value":13},{"text":"Цахилгаан","value":14},{"text":"Хуурай \u0026 нойт орчинд соронзонгоор ангилах","value":15},{"text":"Гравитаци \u0026 Уусгалт","value":16},{"text":"Гравитаци \u0026 Флотаци","value":17},{"text":"Гравитаци \u0026 Соронзон","value":18},{"text":"Эцсийн бүтээгдэхүүн","value":19},{"text":"Уриншуулах","value":20},{"text":"Баяжуулалт хийгдэхгүй","value":21}];
      var lutmintype=[{"text":"Ил","value":1},{"text":"Далд","value":2},{"text":"Хосолсон","value":3}];
      var lutminerals=[{"text":"Алт","value":1,"parentid":1},{"text":"Базальт","value":2,"parentid":2},{"text":"У/биндэрьяа","value":3,"parentid":2},{"text":"Битум","value":4,"parentid":2},{"text":"Нүүрс","value":5,"parentid":8},{"text":"Бар/м","value":6,"parentid":2},{"text":"Зэс","value":7,"parentid":1},{"text":"Болор","value":8,"parentid":2},{"text":"Жонш","value":9,"parentid":2},{"text":"Анар","value":10,"parentid":2},{"text":"Боржин","value":11,"parentid":2},{"text":"Ногоон боржин","value":12,"parentid":2},{"text":"Гөлтгөнө","value":13,"parentid":2},{"text":"Төмөр","value":14,"parentid":1},{"text":"Манган","value":15,"parentid":1},{"text":"Э/шавар","value":16,"parentid":2},{"text":"Гялтгануур","value":17,"parentid":2},{"text":"Mолибден","value":18,"parentid":1},{"text":"Перлит","value":19,"parentid":2},{"text":"Фосфорит","value":20,"parentid":2},{"text":"Давс","value":21,"parentid":2},{"text":"Мөнгө","value":22,"parentid":1},{"text":"Гянтболд","value":24,"parentid":1},{"text":"Уран","value":25,"parentid":1},{"text":"Цеолит","value":26,"parentid":2},{"text":"Цайр","value":27,"parentid":1},{"text":"Хар тугалга","value":28,"parentid":1},{"text":"Тодорхойгүй","value":29,"parentid":2},{"text":"Никель","value":30,"parentid":1},{"text":"Кобальт","value":31,"parentid":2},{"text":"Хөнгөн цагаан","value":32,"parentid":1},{"text":"Титан","value":33,"parentid":2},{"text":"Хром","value":34,"parentid":2},{"text":"Мөнгөлжин","value":35,"parentid":1},{"text":"Висмут","value":36,"parentid":2},{"text":"Тантал","value":37,"parentid":2},{"text":"Ниовиум","value":38,"parentid":2},{"text":"Литийн давс","value":39,"parentid":2},{"text":"Кадми","value":40,"parentid":2},{"text":"Иттрий","value":41,"parentid":2},{"text":"Мөнгөн цагаан","value":42,"parentid":1},{"text":"Циркон","value":43,"parentid":2},{"text":"Лантан","value":44,"parentid":2},{"text":"Цери","value":45,"parentid":1},{"text":"Хүнцэл","value":46,"parentid":2},{"text":"Антимонит","value":47,"parentid":2},{"text":"Мөнгөн ус","value":48,"parentid":2},{"text":"Асвест","value":49,"parentid":2},{"text":"Мусковити","value":50,"parentid":2},{"text":"Магнести","value":51,"parentid":1},{"text":"Танар","value":52,"parentid":2},{"text":"Бал чулуу","value":53,"parentid":1},{"text":"Хүхэр","value":54,"parentid":2},{"text":"Барий","value":55,"parentid":2},{"text":"Вольфрам","value":56,"parentid":2},{"text":"Хүдэр","value":57,"parentid":1},{"text":"Холимог металл","value":58,"parentid":1},{"text":"Ховор металл","value":59,"parentid":1},{"text":"Полиметалл","value":60,"parentid":1},{"text":"Өнгөт металл","value":61,"parentid":1},{"text":"Мөсөн шүү","value":63,"parentid":2},{"text":"Агальметолит","value":64,"parentid":2},{"text":"Цагаан тугалга","value":65,"parentid":1},{"text":"Литий","value":66,"parentid":1},{"text":"Шатдаг занар","value":386,"parentid":2}];
      var yesnodata=[{"text":"Тийм","value":1},{"text":"Үгүй","value":0}];
      var measurement=[{"text":"мян.м3","value":1},{"text":"мян.тн","value":2},{"text":"мян.тн","value":22},{"text":"мян.тн","value":27},{"text":"г/м3","value":3},{"text":"мян.м3 кг","value":4},{"text":"г/тн","value":5},{"text":"тн","value":5}];
      var bombtypes=[{"text":"Гэрээгээр гүйцэтгүүлэх","value":2},{"text":"Өөрөө гүйцэтгэх","value":1},{"text":"Үгүй","value":3}];
      var deposits=[{text: "Алт (Үндсэн)", value: 1},{text: "Алт (Шороон)",value: 2},{text: "Зэс",value: 3},{text: "Мөнгө",value: 4},{text: "Төмөр",value: 5},
    	  {text: "Хүрэн",value: 6},{text: "Чулуун",value: 7},{text: "Антрацит",value: 8},{text: "Шатдаг занар",value: 9},{text: "Алт (Үүсмэл)",value: 10},{text: "Гянтболд (Үндсэн)",value: 11},
    	  {text: "Гянтболд (Шороон)",value: 12},{text: "Лити",value: 13},{text: "Магни",value: 14},{text: "Манган",value: 15},{text: "Молибден",value: 16},{text: "Уран",value: 17},
    	  {text: "Усан биндэрьяа",value: 18},{text: "Холимог металл ",value: 19},{text: "Цагаантугалга (Шороон)",value: 20},{text: "Цайр",value: 21},{text: "Цагаантугалга-Гянтболд (Шороон)",value: 22},{text: "Цагаантугалга-Гянтболд (Үүсмэл)",value: 23},
    	  {text: "Бар/м (Алевролит)",value: 24},{text: "Бар/м (Битумжсэн элсэн чулуу)",value: 25},{text: "Бар/м (Галт уулын шаарга)",value: 26},{text: "Бар/м (Гантиг)",value: 27},{text: "Бар/м (Дайрга)",value: 28},
    	  {text: "Бар/м (Доломит)",value: 29},{text: "Бар/м (Ногоон хаш)",value: 30},{text: "Бар/м (Шавар)",value: 31},{text: "Бар/м (Шохойн чулуу)",value: 32},{text: "Бар/м (Цемент)",value: 33},{text: "Бар/м (Элс)",value: 34},{text: "Бар/м (Элс, хайрга)",value: 35},{text: "Бар/м (Элсэн чулуу)",value: 36},{text: "Битум",value: 37},{text: "Болор",value: 38},{text: "Боржин",value: 39},{text: "Гөлтгөнө",value: 40},{text: "Давс",value: 41},{text: "Мөсөн шүү",value: 42},{text: "Перлит",value: 43},{text: "Фосфорит",value: 44},{text: "Хайлуур жонш",value: 45},{text: "Цеолит",value: 46},{text: "Газрын ховор элемент",value: 47},{text: "Бал чулуу",value: 48},{text: "Базальт",value: 49}];
      $scope.proleGrid = {
    	      dataSource: {

    	        transport: {
    	          read: {
    	            url: "/user/angular/DataMinPlan1",
    	            contentType: "application/json; charset=UTF-8",
    	            type: "POST",
    	            data: {"customJoins":"select d.id , t.lpName, t.licenseXB, a.horde,a.aimagid, a.sumid, a.mineralid,a.deposidid,a.countries,a.appdate,a.yearcapacity,a.workyear, a.minetypeid,a.statebudgetid, a.concetrate, a.komissid, a.komissdate,a.komissakt,a.startdate,a.bombid,a.bombtype, d.data1, d.data2, d.data3, d.data4, d.data5, d.data6, d.data7, d.data8, d.data9, d.noteid, d.planid, d.dataIndex from  AnnualRegistration t, DataMinPlan1 d, LnkReqAnn a  where t.divisionid=1 and d.noteid=185 and reporttype=3 and t.id in ("+dt+") and a.reqid=t.reqid and t.id=d.planid and t.repstatusid=1 group by d.id , t.lpName, t.licenseXB, a.horde,a.aimagid, a.sumid, a.mineralid,a.deposidid,a.countries,a.appdate,a.yearcapacity,a.workyear, a.minetypeid,a.statebudgetid, a.concetrate, a.komissid, a.komissdate,a.komissakt,a.startdate,a.bombid,a.bombtype, d.data1, d.data2, d.data3, d.data4, d.data5, d.data6, d.data7, d.data8, d.data9, d.noteid, d.planid, d.dataIndex order by d.id asc"}
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
    	        /*group: {
    	          field: "lpName",
    	          dir: "desc"
    	        },*/
    	        pageSize: 500,
    	        serverPaging: true,
    	        serverFiltering: true,
    	        serverSorting: true,
    	        sort: {
    	          field: "d.id",
    	          dir: "asc"
    	        }
    	      },
    	      toolbar: ["excel"],
    	      excel: {
    	          fileName: "Report.xlsx",
    	          allPages: true
    	      },
    	      height:function(){
              	return $( document ).height() * 0.7;
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
    	        field: "lpName",
    	        title: "ААН",
    	        hidden: false,
    	        width: 200
    	      },    	      
    	      {
    	          field: "licenseXB",
    	          title: "Тусгай зөвшөөрлийн дугаар",
    	          hidden: false,
    	          width: 150
    	        }, {
    	        title: "Бүтээгдэхүүн",
    	        columns: [{
    	          field: "dataIndex",
    	          title: "#",
    	          width: 75
    	        }, {
    	          field: "data1",
    	          title: "Үзүүлэлт",
    	          width: 200
    	        }, {
    	          field: "data2",
    	          title: "Х/нэгж",
    	          width: 90,
    	          values:measurement
    	        }, {
    	          field: "data3",
    	          title: "Тоон утга",
    	          width: 75
    	        }]
    	      }, {
    	        title: "Ашигт малтмалын нэр",
    	        columns: [{
    	          field: "data4",
    	          title: "№",
    	          width: 75
    	        }, {
    	          field: "data5",
    	          title: "Ашигт малтмалын нэр",
    	          width: 120
    	        }, ]
    	      }, {
    	        title: "Агуулга",
    	        columns: [{
    	          field: "data6",
    	          title: "Х/нэгж",
    	          width: 90
    	        }, {
    	          field: "data7",
    	          title: "Тоон утга",
    	          width: 75
    	        }, ]
    	      }, {
    	        title: "Метал (эрдэс) хэмжээ",
    	        columns: [{
    	          field: "data8",
    	          title: "Х/нэгж",
    	          width: 90
    	        }, {
    	          field: "data9",
    	          title: "Тоон утга",
    	          width: 75
    	        }]
    	      },
    	      {
    	          field: "horde",
    	          title: "Ордын нэр",
    	          hidden: false,
    	          width: 150
    	      },
    	      {
      	          field: "aimagid",
      	          title: "Аймаг / Нийслэл...",
      	          hidden: false,
      	          width: 150
      	      },
      	      {
		          field: "sumid",
		          title: "Сум / Дүүрэг...",
		          hidden: false,
		          width: 150
		      },
		      {
		          field: "mineralid",
		          title: "Ашигт малтмалын нэр",
		          hidden: false,
		          width: 150,
		          values:lutminerals
		      },
		      {
		          field: "deposidid",
		          title: "Ашигт малтмалын төрөл",
		          hidden: false,
		          width: 150,
		          values:deposits
		      },
		      {
		          field: "countries",
		          title: "Хөрөнгө оруулалт /Улсын нэр/",
		          hidden: false,
		          width: 150
		      }, 
		      {
		          field: "appdate",
		          title: "ТЭЗҮ хүлээн авсан/батлуулсан (он, сар, өдөр)",
		          hidden: false,
		          width: 150
		      },
		      {
		          field: "yearcapacity",
		          title: "ТЭЗҮ-д тооцсон жилийн хүч чадал (Мян.тн/жил)",
		          hidden: false,
		          width: 150
		      },
		      {
		          field: "workyear",
		          title: "Ашиглах нийт хугацаа (Жилээр)/",
		          hidden: false,
		          width: 150
		      },
		      {
		          field: "minetypeid",
		          title: "Ашиглалтын технологи",
		          hidden: false,
		          width: 150,
		          values:lutmintype
		      },
		      {
		          field: "statebudgetid",
		          title: "Улсын төсвийн хөрөнгөөр хийсэн хайгуулын ажлын нөхөн төлбөрийн гэрээтэй эсэх",
		          hidden: false,
		          width: 150,
		          values:yesnodata
		      },
		      {
		          field: "concetrate",
		          title: "Баяжуулалтын технологи",
		          hidden: false,
		          width: 150,
		          values:concentration
		      },
		      {
		          field: "komissid",
		          title: "Улсын комисс ажилласан тухай",
		          hidden: false,
		          width: 150,
		          values:yesnodata
		      },
		      {
		          field: "komissdate",
		          title: "Ашиглалтанд хүлээж авсан огноо",
		          hidden: false,
		          width: 150
		      },
		      {
		          field: "komissakt",
		          title: "Актын дугаарыг шивэх/",
		          hidden: false,
		          width: 150
		      },
		      {
		          field: "startdate",
		          title: "Ашиглалт эхэлсэн огноо",
		          hidden: false,
		          width: 150
		      },
		      {
		          field: "bombid",
		          title: "Тэсэлгээний ажил.",
		          hidden: false,
		          width: 150,
		          values:yesnodata
		      },
		      {
		          field: "bombtype",
		          title: "Гүйцэтгэгч...",
		          hidden: false,
		          width: 150,
		          values:bombtypes
		      }
    	      ],
    	      editable: false
    	    };
    });

  }]);