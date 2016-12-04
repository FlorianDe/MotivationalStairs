/*
 Code to generate some highscores
 function genHigh(name, score) {
 return {
 user: {
 id: Math.floor(Math.random() * 100000),
 name: name
 },
 score: score,
 date: new Date(Date.now() - Math.floor(Math.random() * 8640000000)).toLocaleDateString()
 };
 }

 function caseName(name) {
 return name.substr(0,1) + name.toLowerCase().substr(1);
 }

 var firstNames = ["MARY","PATRICIA","LINDA","BARBARA","ELIZABETH","JENNIFER","MARIA","SUSAN","MARGARET","DOROTHY","LISA","NANCY","KAREN","BETTY","HELEN","SANDRA","DONNA","CAROL","RUTH","SHARON","MICHELLE","LAURA","SARAH","KIMBERLY","DEBORAH","JESSICA","SHIRLEY","CYNTHIA","ANGELA","MELISSA","BRENDA","AMY","ANNA","REBECCA","VIRGINIA","KATHLEEN","PAMELA","MARTHA","DEBRA","AMANDA","STEPHANIE","CAROLYN","CHRISTINE","MARIE","JANET","CATHERINE","FRANCES","ANN","JOYCE","DIANE","ALICE","JULIE","HEATHER","TERESA","DORIS","GLORIA","EVELYN","JEAN","CHERYL","MILDRED","KATHERINE","JOAN","ASHLEY","JUDITH","ROSE","JANICE","KELLY","NICOLE","JUDY","CHRISTINA","KATHY","THERESA","BEVERLY","DENISE","TAMMY","IRENE","JANE","LORI","RACHEL","MARILYN","ANDREA","KATHRYN","LOUISE","SARA","ANNE","JACQUELINE","WANDA","BONNIE","JULIA","RUBY","LOIS","TINA","PHYLLIS","NORMA","PAULA","DIANA","ANNIE","LILLIAN","EMILY","ROBIN","PEGGY","CRYSTAL","GLADYS","RITA","DAWN","CONNIE","FLORENCE","TRACY","EDNA","TIFFANY","CARMEN","ROSA","CINDY","GRACE","WENDY","VICTORIA","EDITH","KIM","SHERRY","SYLVIA","JOSEPHINE","THELMA","SHANNON","SHEILA","ETHEL","ELLEN","ELAINE","MARJORIE","CARRIE","CHARLOTTE","MONICA","ESTHER","PAULINE","EMMA","JUANITA","ANITA","RHONDA","HAZEL","AMBER","EVA","DEBBIE","APRIL","LESLIE","CLARA","LUCILLE","JAMIE","JOANNE","ELEANOR","VALERIE","DANIELLE","MEGAN","ALICIA","SUZANNE","MICHELE","GAIL","BERTHA","DARLENE","VERONICA","JILL","ERIN","GERALDINE","LAUREN","CATHY","JOANN","LORRAINE","LYNN","SALLY","REGINA","ERICA","BEATRICE","DOLORES","BERNICE","AUDREY","YVONNE","ANNETTE","JUNE","SAMANTHA","JAMES","JOHN","ROBERT","MICHAEL","WILLIAM","DAVID","RICHARD","CHARLES","JOSEPH","THOMAS","CHRISTOPHER","DANIEL","PAUL","MARK","DONALD","GEORGE","KENNETH","STEVEN","EDWARD","BRIAN","RONALD","ANTHONY","KEVIN","JASON","MATTHEW","GARY","TIMOTHY","JOSE","LARRY","JEFFREY","FRANK","SCOTT","ERIC","STEPHEN","ANDREW","RAYMOND","GREGORY","JOSHUA","JERRY","DENNIS","WALTER","PATRICK","PETER","HAROLD","DOUGLAS","HENRY","CARL","ARTHUR","RYAN","ROGER","JOE","JUAN","JACK","ALBERT","JONATHAN","JUSTIN","TERRY","GERALD","KEITH","SAMUEL","WILLIE","RALPH","LAWRENCE","NICHOLAS","ROY","BENJAMIN","BRUCE","BRANDON","ADAM","HARRY","FRED","WAYNE","BILLY","STEVE","LOUIS","JEREMY","AARON","RANDY","HOWARD","EUGENE","CARLOS"];

 var lastNames = ["SMITH", "JOHNSON", "WILLIAMS", "JONES", "BROWN", "DAVIS", "MILLER", "WILSON", "MOORE", "TAYLOR", "ANDERSON", "THOMAS", "JACKSON", "WHITE", "HARRIS", "MARTIN", "THOMPSON", "GARCIA", "MARTINEZ", "ROBINSON", "CLARK", "RODRIGUEZ", "LEWIS", "LEE", "WALKER", "HALL", "ALLEN", "YOUNG", "HERNANDEZ", "KING", "WRIGHT", "LOPEZ", "HILL", "SCOTT", "GREEN", "ADAMS", "BAKER", "GONZALEZ", "NELSON", "CARTER", "MITCHELL", "PEREZ", "ROBERTS", "TURNER", "PHILLIPS", "CAMPBELL", "PARKER", "EVANS", "EDWARDS", "COLLINS", "STEWART", "SANCHEZ", "MORRIS", "ROGERS", "REED", "COOK", "MORGAN", "BELL", "MURPHY", "BAILEY", "RIVERA", "COOPER", "RICHARDSON", "COX", "HOWARD", "WARD", "TORRES", "PETERSON", "GRAY", "RAMIREZ", "JAMES", "WATSON", "BROOKS", "KELLY", "SANDERS", "PRICE", "BENNETT", "WOOD", "BARNES", "HENDERSON"];

 function scrambleName() {
 return caseName(firstNames[Math.floor(Math.random() * firstNames.length)]) +
 " " + caseName(lastNames[Math.floor(Math.random() * lastNames.length)]);
 }

 var scores = [];

 for(var i = 0; i < 200; i++) {
 scores.push(genHigh(scrambleName(), Math.floor(Math.random() * 1000) + 30));
 }

 console.log(JSON.stringify(scores));
 */


export const dummyScores = [{
    "user": {"id": 68937, "name": "Doris Rivera"},
    "score": 134,
    "date": "4.11.2016"
}, {"user": {"id": 17970, "name": "Laura Edwards"}, "score": 280, "date": "21.10.2016"}, {
    "user": {
        "id": 66266,
        "name": "Christina Wilson"
    }, "score": 722, "date": "19.11.2016"
}, {"user": {"id": 71850, "name": "Marie Murphy"}, "score": 406, "date": "7.9.2016"}, {
    "user": {
        "id": 90911,
        "name": "Lois Moore"
    }, "score": 971, "date": "18.11.2016"
}, {"user": {"id": 37190, "name": "Michelle Brown"}, "score": 197, "date": "17.11.2016"}, {
    "user": {
        "id": 58858,
        "name": "Justin Scott"
    }, "score": 265, "date": "17.10.2016"
}, {"user": {"id": 50942, "name": "Melissa Rivera"}, "score": 615, "date": "11.10.2016"}, {
    "user": {
        "id": 78741,
        "name": "Douglas Martinez"
    }, "score": 71, "date": "18.11.2016"
}, {"user": {"id": 67814, "name": "Lois Green"}, "score": 40, "date": "2.12.2016"}, {
    "user": {
        "id": 82761,
        "name": "Michael Stewart"
    }, "score": 557, "date": "27.11.2016"
}, {"user": {"id": 58032, "name": "Diane Wright"}, "score": 396, "date": "22.9.2016"}, {
    "user": {
        "id": 50603,
        "name": "Frances Sanchez"
    }, "score": 273, "date": "27.9.2016"
}, {"user": {"id": 14628, "name": "Cindy Cooper"}, "score": 492, "date": "7.10.2016"}, {
    "user": {
        "id": 6844,
        "name": "Teresa Cook"
    }, "score": 216, "date": "16.11.2016"
}, {"user": {"id": 67159, "name": "Rhonda Ramirez"}, "score": 423, "date": "14.9.2016"}, {
    "user": {
        "id": 70053,
        "name": "Tina Sanchez"
    }, "score": 838, "date": "29.11.2016"
}, {"user": {"id": 26701, "name": "Jose Bell"}, "score": 643, "date": "20.10.2016"}, {
    "user": {
        "id": 62531,
        "name": "Angela Sanders"
    }, "score": 869, "date": "25.9.2016"
}, {"user": {"id": 90883, "name": "Gary Sanchez"}, "score": 617, "date": "18.10.2016"}, {
    "user": {
        "id": 97392,
        "name": "Debbie Rogers"
    }, "score": 710, "date": "25.11.2016"
}, {
    "user": {"id": 1922, "name": "Elizabeth Richardson"},
    "score": 269,
    "date": "13.9.2016"
}, {"user": {"id": 56378, "name": "Ruby Thomas"}, "score": 267, "date": "22.9.2016"}, {
    "user": {
        "id": 62566,
        "name": "Joshua Walker"
    }, "score": 736, "date": "6.9.2016"
}, {
    "user": {"id": 91827, "name": "Beatrice Richardson"},
    "score": 211,
    "date": "22.9.2016"
}, {"user": {"id": 3353, "name": "Deborah Miller"}, "score": 646, "date": "17.11.2016"}, {
    "user": {
        "id": 68518,
        "name": "Lucille Ward"
    }, "score": 1018, "date": "22.9.2016"
}, {"user": {"id": 83646, "name": "Patricia Davis"}, "score": 790, "date": "18.9.2016"}, {
    "user": {
        "id": 21076,
        "name": "Nancy Mitchell"
    }, "score": 119, "date": "29.9.2016"
}, {"user": {"id": 42035, "name": "Teresa Edwards"}, "score": 243, "date": "23.11.2016"}, {
    "user": {
        "id": 21743,
        "name": "Raymond Lee"
    }, "score": 928, "date": "1.11.2016"
}, {
    "user": {"id": 87885, "name": "Kimberly Robinson"},
    "score": 552,
    "date": "19.10.2016"
}, {
    "user": {"id": 35348, "name": "Bonnie Rodriguez"},
    "score": 1012,
    "date": "11.9.2016"
}, {"user": {"id": 75286, "name": "Sally Jones"}, "score": 622, "date": "14.11.2016"}, {
    "user": {
        "id": 75991,
        "name": "Norma Bennett"
    }, "score": 585, "date": "25.9.2016"
}, {"user": {"id": 5375, "name": "Grace Ramirez"}, "score": 532, "date": "22.11.2016"}, {
    "user": {
        "id": 58937,
        "name": "Rachel Cooper"
    }, "score": 269, "date": "19.10.2016"
}, {"user": {"id": 92007, "name": "Suzanne Hill"}, "score": 274, "date": "13.10.2016"}, {
    "user": {
        "id": 53177,
        "name": "Lorraine Gonzalez"
    }, "score": 673, "date": "29.10.2016"
}, {"user": {"id": 143, "name": "Pamela Jones"}, "score": 980, "date": "1.11.2016"}, {
    "user": {
        "id": 68155,
        "name": "Teresa Hill"
    }, "score": 961, "date": "21.9.2016"
}, {"user": {"id": 38544, "name": "Nancy Baker"}, "score": 860, "date": "2.9.2016"}, {
    "user": {
        "id": 27493,
        "name": "Harry Brooks"
    }, "score": 690, "date": "8.10.2016"
}, {
    "user": {"id": 31301, "name": "Marjorie Rogers"},
    "score": 254,
    "date": "27.10.2016"
}, {"user": {"id": 94939, "name": "Joseph King"}, "score": 774, "date": "7.9.2016"}, {
    "user": {
        "id": 53641,
        "name": "Scott Perez"
    }, "score": 463, "date": "15.9.2016"
}, {
    "user": {"id": 15358, "name": "Valerie Rodriguez"},
    "score": 274,
    "date": "7.9.2016"
}, {"user": {"id": 73997, "name": "Louis Morris"}, "score": 288, "date": "2.12.2016"}, {
    "user": {
        "id": 64637,
        "name": "Edith Lopez"
    }, "score": 130, "date": "7.10.2016"
}, {"user": {"id": 81655, "name": "Eugene Howard"}, "score": 102, "date": "2.11.2016"}, {
    "user": {
        "id": 77088,
        "name": "Helen Morgan"
    }, "score": 851, "date": "28.10.2016"
}, {"user": {"id": 4767, "name": "Donna Davis"}, "score": 290, "date": "15.10.2016"}, {
    "user": {
        "id": 76304,
        "name": "John Allen"
    }, "score": 743, "date": "31.10.2016"
}, {"user": {"id": 92929, "name": "Florence Lewis"}, "score": 904, "date": "13.10.2016"}, {
    "user": {
        "id": 66012,
        "name": "Victoria Mitchell"
    }, "score": 462, "date": "14.10.2016"
}, {"user": {"id": 71693, "name": "Joann Allen"}, "score": 48, "date": "23.9.2016"}, {
    "user": {
        "id": 83691,
        "name": "Cindy Morris"
    }, "score": 50, "date": "17.11.2016"
}, {"user": {"id": 7349, "name": "Audrey Walker"}, "score": 372, "date": "29.11.2016"}, {
    "user": {
        "id": 2715,
        "name": "Anne Robinson"
    }, "score": 522, "date": "6.9.2016"
}, {"user": {"id": 83946, "name": "Billy Hall"}, "score": 379, "date": "1.10.2016"}, {
    "user": {
        "id": 30897,
        "name": "Darlene Garcia"
    }, "score": 346, "date": "10.9.2016"
}, {"user": {"id": 26619, "name": "Aaron Johnson"}, "score": 562, "date": "11.10.2016"}, {
    "user": {
        "id": 12314,
        "name": "Dawn Harris"
    }, "score": 330, "date": "21.9.2016"
}, {"user": {"id": 20409, "name": "Yvonne Morgan"}, "score": 438, "date": "13.9.2016"}, {
    "user": {
        "id": 64759,
        "name": "Carol Richardson"
    }, "score": 997, "date": "28.11.2016"
}, {"user": {"id": 74283, "name": "Carmen Bennett"}, "score": 946, "date": "12.10.2016"}, {
    "user": {
        "id": 60736,
        "name": "Janet Roberts"
    }, "score": 776, "date": "20.9.2016"
}, {"user": {"id": 45164, "name": "Kevin Howard"}, "score": 151, "date": "17.11.2016"}, {
    "user": {
        "id": 55418,
        "name": "Ellen Smith"
    }, "score": 466, "date": "30.11.2016"
}, {"user": {"id": 65788, "name": "June Turner"}, "score": 646, "date": "27.8.2016"}, {
    "user": {
        "id": 10403,
        "name": "Judy Bennett"
    }, "score": 567, "date": "24.11.2016"
}];