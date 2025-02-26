<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Mail;
use App\Mail\GenericoEmail;
use App\Models\Contato;
use App\Models\TagHumano;

class EnviarEmailController extends Controller
{
    public function enviarEmailGenerico(Request $request)
    {
        $emails = [];
        if ($request->email) {
            array_push($emails, $request->email);
        }
        if ($request->tagHumano) {
            try {
                $idUsuario = TagHumano::find($request->tagHumano);
                $contatos = Contato::where('id_usuario', $idUsuario->id_usuario)->where('tp_contato', "Email")->get();
                foreach ($contatos as $key => $value) {
                    array_push($emails, $value->ds_contato);
                }
                // Mail::to($emails)->send(new GenericoEmail($request->all()));
                dump($emails);
                Mail::to($emails)->bcc("wiliansaugusto@gmail.com")->send(new GenericoEmail($request->all()));
                return response()->json(['message' => 'Email enviado com sucesso!'], 201);
            } catch (\Exception $e) {
                return response()->json(['message' => 'Problemas para enviar o email!'], 500);
            }
        }
    }
}
