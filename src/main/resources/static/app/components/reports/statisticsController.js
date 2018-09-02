angular
    .module('altairApp')
    .controller('statisticsCtrl', [
        '$rootScope',
        '$scope',
        '$interval',
        '$window',
        '$timeout',
        'variables',
        'stat1',
        'stat2',
        'stat3',
        'stat4',
        'minerals',
        'deposits',
        'lnkbutdep',
        function ($rootScope, $scope, $interval, $window, $timeout, variables, stat1, stat2,stat3,stat4, minerals,deposits,lnkbutdep) {
            $scope.stat1 = stat1;
            $scope.stat2 = stat2;
            $scope.stat3 = stat3;
            $scope.stat4 = stat4;
            /*$scope.minerals = [];
            for(var i=0;i<minerals.length;i++){
                var mineralTemp = {};
                mineralTemp.id = minerals[i].value;
                mineralTemp.name = minerals[i].text;
                mineralTemp.buts = [];
                mineralTemp.stats = [];
                for(var j=0;j<deposits.length;j++){
                    if (minerals[i].value == deposits[j].parentid){
                        for(var k=0;k<lnkbutdep.length;k++){
                            if (deposits[j].value == lnkbutdep[k].depid && mineralTemp.buts.indexOf(lnkbutdep[k].butid) == -1){
                                mineralTemp.buts.push(lnkbutdep[k].butid);
                            }
                        }
                    }
                }
                $scope.minerals.push(mineralTemp);
            }
            for(var t=0;t<stat3.length;t++){
                for(var t1=0;t1<$scope.minerals.length;t1++){
                    for(var t2=0;t2<$scope.minerals[t1].buts.length;t2++){
                        if (stat3[t][0] == $scope.minerals[t1].buts[t2]){
                            $scope.minerals[t1].stats.push(stat3[t]);
                        }
                    }
                }
            }*/
            /*$scope.minerals4 = [];
            for(var i=0;i<minerals.length;i++){
                var mineralTemp = {};
                mineralTemp.id = minerals[i].value;
                mineralTemp.name = minerals[i].text;
                mineralTemp.buts = [];
                mineralTemp.stats = [];
                for(var j=0;j<deposits.length;j++){
                    if (minerals[i].value == deposits[j].parentid){
                        for(var k=0;k<lnkbutdep.length;k++){
                            if (deposits[j].value == lnkbutdep[k].depid && mineralTemp.buts.indexOf(lnkbutdep[k].butid) == -1){
                                mineralTemp.buts.push(lnkbutdep[k].butid);
                            }
                        }
                    }
                }
                $scope.minerals4.push(mineralTemp);
            }
            for(var t=0;t<stat4.length;t++){
                for(var t1=0;t1<$scope.minerals4.length;t1++){
                    for(var t2=0;t2<$scope.minerals4[t1].buts.length;t2++){
                        if (stat3[t][0] == $scope.minerals4[t1].buts[t2]){
                            $scope.minerals4[t1].stats.push(stat4[t]);
                        }
                    }
                }
            }*/
        }
    ])

;