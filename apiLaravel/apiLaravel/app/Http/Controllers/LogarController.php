<?php

namespace App\Http\Controllers;

use App\Models\Usuario;
use Illuminate\Http\Request;
use Illuminate\Validation\ValidationException;
use App\Mail\EsqueciSenha;
use App\Mail\EsqueciSenhaEmail;
use Illuminate\Support\Facades\Mail;

class LogarController extends Controller
{
    public function login(Request $request)
    {
        try {
            $this->validate($request, [
                'senha' => "required"

            ]);
        } catch (ValidationException $e) {
            return response()->json($e->errors(), 400);
        }


        if (isset($request['email'])) {
            $login = Usuario::where('email', $request['email'])->where('senha', $request['senha'])->first();
            if ($login) {
                return response()->json(['valido' => true, 'message' => 'Logado com sucesso!', 'idUsuario' => $login->id_usuario], 200);
            } else {
                return response()->json(['message' => 'Credenciais inválidas!'], 401);
            }
        } elseif ($request['cpf']) {
            $login = Usuario::where('cpf', $request['cpf'])->where('senha', $request['senha'])->first();
            if ($login) {
                return response()->json(['valido' => true, 'message' => 'Logado com sucesso!', 'idUsuario' => $login->id_usuario], 200);
            } else {
                return response()->json(['message' => 'Credenciais inválidas!'], 401);
            }
        }
    }
    public function esqueciSenha(Request $request)
    {
        try {
            $this->validate($request, [
                'email' => "required"
            ]);
            $usuario = Usuario::where('email', $request['email'])->first();
        } catch (ValidationException $e) {
            return response()->json($e->errors(), 400);
        }

        if ($usuario) {
            $senhaNova = '@Chei' . rand(10000, 99999);
            $usuario->senha = $senhaNova;
            $usuario->save();
            try {
                $data = [
                    'senha' => $senhaNova,
                    'nome' => $usuario->nomeCompleto
                ];
                Mail::to($usuario->email)->send(new EsqueciSenhaEmail($data));
                return response()->json(['message' => 'Email enviado com sucesso para o usuario: ' . $usuario->nomeCompleto], 200);
            } catch (\Exception $e) {
                return response()->json(['message' => 'Erro ao enviar email!'], 500);
            }
        } else {
            return response()->json(['message' => 'Email não encontrado!'], 404);
        }
    }
}
