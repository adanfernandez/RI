<?php

require 'vendor/autoload.php';

$client = Elasticsearch\ClientBuilder::create()->build();

/*

Visita https://www.elastic.co/guide/en/elasticsearch/client/php-api/current/_indexing_documents.html
para m�s informaci�n sobre indexado de documentos.

Por cuestiones de eficiencia vamos a utilizar lo que se denomina "bulk indexing"
que nos permite indexar m�ltiples documentos en lotes.

Experimenta con el n�mero de documentos de cada lote para ver c�mo cambia el 
rendimiento del indexado

�Atenci�n! Si no est�s creando el �ndice sobre un SSD el indexado va a tardar un rato

*/

$filename="2008-Feb-02-04";

$f=fopen("".$filename.".json","r");

$max=10000;
$count=0;
$total=0;

while(!feof($f)) {
	$line=fgets($f);

	// Se transforma la l�nea en un documento v�lido para ElasticSearch
	// N�tese que en PHP un documento v�lido ser� un array asociativo
	//
	$doc=json_decode($line,true); // Decodificamos la cadena JSON como un array asociativo
	$doc=$doc["_source"]; // Queremos los datos del tuit original

	$count++;
	$total++;

	if ($total%10000==0) echo $total."\n";

	if ($count<$max) {
		// Acumulamos el documento en un lote
		//
		$lote[]=$doc;
	} else {
		// Creamos los par�metros para el bulk indexing
		//
		foreach ($lote as $doc) {
			// Primero van los metadatos del documento
			//
			$params['body'][] = [
				'index' => [
					'_index' => strtolower($filename),	// Puedes nombrar el �ndice como quieras pero en min�sculas
					'_type'  => 'tweet',				// Puedes poner el tipo que te apetezca
					"_id"    => $doc["id_str"]			// Queremos usar como _id del documento el campo id_str del tuit
				]
			];

			// Luego van los datos propiamente dichos
			//
			$params['body'][] = $doc;
		}

		// Enviamos el lote a ElasticSearch para su indexado
		//
		$responses = $client->bulk($params);

//		print_r($responses);	// Las respuestas deber�an procesarse para ver si hubo errores

		echo $total."\n";

		// Destruimos las variables $lote y $params comenzamos de nuevo prestando atenci�n
		// a no perder la l�nea que acabamos de leer
		//
		unset($lote);
		unset($params);
		$count=0;
		$lote[]=$doc;
	}
}

?>