<?php

require "vendor/autoload.php";

$client = Elasticsearch\ClientBuilder::create()->build();

/*

Visita https://www.elastic.co/guide/en/elasticsearch/client/php-api/current/_search_operations.html
para más información sobre consulta de documentos

*/

if(!function_exists("readline")) {
    function readline($prompt = null){
        if($prompt){
            echo $prompt;
        }
        $fp = fopen("php://stdin","r");
        $line = rtrim(fgets($fp, 1024));
        return $line;
    }
}

$tema = readline('Sobre que tema quieres usuarios');

// 2008-feb-02-04-en/_search?q=text:sport*
//
$params = [
	"index" => "2008-feb-02-04",
	"type" => "tweet",
	"body" => [
        "size" => 0,
        "query" => [
            "bool" => [
                "must" =>
                [
                    [
                        "match"=>[
                            "lang"=>"en"
                        ]
                    ]
                    ,
                    [
                        "match"=>[
                            "text"=>$tema
                        ]
                    ]
                ]
            ]
        ],
        "aggs"=>[
        
            "Palabras"=>[
                "significant_terms"=>
                [
                    "field"=>"text",
                    "size"=> 10
                ]
            ]
        
        ]
    ]
];

$results = $client->search($params);

//print_r($results);





$terminos = [];
foreach($results['aggregations']['Palabras']['buckets'] as $value) {
    echo $value['key'], ': ', $value['score'];
    $terminos[] = [
    'match'=>[
        'text' => [
            
            'query'=> $value['key'],
            'boost'=> $value['score']
            
            ]
        ]
    ];
}


$params = [
	"index" => "2008-feb-02-04",
	"type" => "tweet",
	"body" => [
        "size" => 0,
        "query" => [
            "bool" => [
                "should" => $terminos
                ]
            ],
        "aggs"=>[
            "numero_usuarios"=> 
            [
                "terms"=>
                [
                    "field"=> "user_id_str"
                ]
            ]
        ]
    ]
];

$results = $client->search($params);

print_r($results);
                
?>