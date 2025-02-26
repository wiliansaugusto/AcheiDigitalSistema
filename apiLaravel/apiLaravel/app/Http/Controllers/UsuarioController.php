<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Http\JsonResponse;
use Illuminate\Validation\ValidationException;
use App\Models\Usuario;
use App\Models\Contato;
use App\Models\Endereco;
use App\Rules\Cpf;
use App\Rules\SenhaRule;
use tidy;

class UsuarioController extends Controller
{
    public function salvarUsuario(Request $request): JsonResponse
    {
        try {
            $this->validate($request, [
                'cpf' => ['required', 'unique:usuarios', new Cpf],
                'email' => 'required|email|unique:usuarios',
                'senha' => ['required', new SenhaRule],
                'nomeCompleto' => 'required|string',
            ]);
        } catch (ValidationException $e) {
            return response()->json($e->errors(), 400);
        }
        $usuario = Usuario::create($request->all());
        $contatos = $request->contato;
        foreach ($contatos as $contato) {
            $novoContato = new Contato($contato);
            $novoContato->id_usuario = $usuario->id_usuario;
            $novoContato->save();
        };
        $enderecos = $request->endereco;
        foreach ($enderecos as $endereco) {
            $novoEndereco = new Endereco($endereco);
            $novoEndereco->id_usuario = $usuario->id_usuario;
            $novoEndereco->save();
        };

        return response()->json([
            "usuario" => $usuario,
            "contatos" => $this->buscarContato($usuario->id_usuario),
            "enderecos" => $this->buscarEndereco($usuario->id_usuario)
        ], 201);
    }

    public function editarUsuario(Request $request): JsonResponse
    {
        $usuario = Usuario::findOrFail($request->idUsuario);
        try {
            $this->validate($request, [
                'cpf' => ['required',  new Cpf],
                'email' => 'required|email',
                'senha' => ['required', new SenhaRule],
                'nomeCompleto' => 'required|string',
            ]);
        } catch (ValidationException $e) {
            return response()->json($e->errors(), 400);
        }

        $usuario->update($request->all());
        return response()->json($usuario, 200);
    }

    public function alterarStatusUsuario($id): JsonResponse
    {

        $usuario = Usuario::findOrFail($id);
        if ($usuario->isAtivo) {
            $usuario->isAtivo = false;
        } else {
            $usuario->isAtivo = true;
        }
        $usuario->save();

        return response()->json($usuario, 200);
    }

    public function listarTodosUsuarios(): JsonResponse
    {
        $usuarios = Usuario::with(['enderecos', 'contatos'])->get();
        return response()->json($usuarios, 200);
    }

    public function listarUsuarioCPF($cpf): JsonResponse
    {
        $usuario = Usuario::where('cpf', $cpf)->firstOrFail();
        $contato = $this->buscarContato($usuario->id_usuario);
        $endereco = $this->buscarEndereco($usuario->id_usuario);
        return response()->json(['usuario' => $usuario, 'contato' => $contato, 'endereco' => $endereco], 200);
    }

    public function listarUsuarioID($id): JsonResponse
    {
        $usuario = Usuario::findOrFail($id);
        $contato = $this->buscarContato($usuario->id_usuario);
        $endereco = $this->buscarEndereco($usuario->id_usuario);
        return response()->json(['usuario' => $usuario, 'contato' => $contato, 'endereco' => $endereco], 200);
    }

    public function buscarEndereco($id)
    {
        $endereco = Endereco::whereHas("usuarios", function ($query) use ($id) {
            $query->where('id_usuario', $id);
        })->get();
        return $endereco;
    }

    public function buscarContato($id)
    {
        $contato = Contato::whereHas("usuarios", function ($query) use ($id) {
            $query->where('id_usuario', $id);
        })->get();
        return $contato;
    }
}
