<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>Protegido Encontrado</title>
    <style>
        .email-header {
            font-size: 24px;
            font-weight: bold;
            color: rgb(255, 255, 255);
            width: 100%;
            height: 60px;
            background-color: #3490dc;
            text-align: center;
            line-height: 60px;
            padding: 5px;
        }

        .email-body {
            font-size: 16px;
            color: #555;
            padding: 20px;
            width: 80%;
        }

        .email-button {
            background-color: #3490dc;
            color: rgb(255, 255, 255);
            padding: 10px 20px;
            border-radius: 5px;
            text-decoration: none;
            font: bold 14px sans-serif;
            margin-bottom: 15px;
        }

        .email-footer {
            font-size: 14px;
            color: #777;
        }
    </style>

</head>

<body>
    <div class="email-header">
        QRCode Scaneado
    </div>
    <div class="email-body">
        <p>Olá, <strong>{{ $data['nome'] }}</strong></p>
        <p>A tag <strong> {{ $data['tagHumano'] }} </strong> seu protegido <strong>{{ $data['protegido'] }}</strong> foi escaneado!</p>
        <p>Segue abaixo os dados de latitude e longitude</p>
        <ul>
            <li>Latitude: {{$data['latitude']}}</li>
            <li>Longitude: {{$data['longitude']}}</li>
        </ul>
        <hr>
        <p>Link para o Google Maps</p>
        <a target="_blank" href="https://www.google.com/maps/search/?api=1&query={{$data['latitude']}},{{$data['longitude']}}">Localização do protegido!</a>
        <hr>

    </div>
    <div class="email-footer">
        <a class=" email-button" href="https://achei.digital">Acessar o site - Achei Digital</a>
        <br><br> Obrigado,<br>
        Equipe Achei Digital
    </div>
    <div>
        <p>Este é um e-mail automático, por favor não responda.</p>
        <p>{{$data['emailMessage']}}</p>
    </div>
</body>

</html>