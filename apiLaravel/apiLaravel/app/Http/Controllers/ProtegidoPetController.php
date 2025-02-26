<?php

namespace App\Http\Controllers;

use App\Models\ProtegidoPet;
use Illuminate\Http\Request;
use Illuminate\Validation\ValidationException;
use Exception;
use App\Models\Usuario;
use Carbon\Carbon;

class ProtegidoPetController extends Controller
{
    public function salvarProtegidoPet(Request $request)
    {
        try {
            $request->validate([
                'nome_protegido_pet' => 'required',
                'usuario.idUsuario' => 'required'
            ]);
        } catch (ValidationException $e) {
            return response()->json($e->errors(), 400);
        }

        try {
            Usuario::findorFail($request->usuario['idUsuario']);
        } catch (Exception $e) {
            return response()->json(['message' => 'Usuario não encontrado'], 400);
        }
        $protegidoPet = new ProtegidoPet();
        $protegidoPet->nome_protegido_pet = $request->nome_protegido_pet;
        $protegidoPet->peso = $request->peso;
        $protegidoPet->patologias = $request->patologias;
        $protegidoPet->uso_medicacoes = $request->uso_medicacoes;
        $protegidoPet->observacoes_gerais = $request->observacoes_gerais;
        $protegidoPet->imagem = $request->imagem;
        $protegidoPet->raca = $request->raca;
        $protegidoPet->especie = $request->especie;
        $protegidoPet->id_usuario = $request->usuario['idUsuario'];
        $protegidoPet->dataNascimento = $request->dataNascimento;
        $protegidoPet->save();

        return response()->json($protegidoPet, 201);
    }

    public function editarProtegidoHumano(Request $request)
    {
        try {
            $this->validate($request, [
                'nome_protegido_pet' => 'required',
                'usuario.idUsuario' => 'required'
            ]);
        } catch (ValidationException $e) {
            return response()->json($e->errors(), 400);
        }

        try {
            $usuario = Usuario::findorFail($request->usuario['idUsuario']);
        } catch (Exception $e) {
            return response()->json(['message' => 'Usuario não encontrado'], 400);
        }

        try {
            $protegidoPet = ProtegidoPet::findOrFail($request->id_protegido_pet);
            if ($protegidoPet->id_usuario != $usuario->id_usuario) {
                return response()->json(['error' => 'Protegido não pertence ao usuário'], 400);
            }
            $protegidoPet->dataNascimento = $request->dataNascimento ? $request->dataNascimento : $protegidoPet->dataNascimento;
            $protegidoPet->dataAlteracao = Carbon::now();
            $protegidoPet->update($request->all());
            return response()->json($protegidoPet, 200);
        } catch (Exception $e) {
            return response()->json(['message' => 'Protegido Pet não encontrado'], 404);
        }
    }
    public function pesquisarProtegidoPetPorID($id)
    {
        try {
            $protegidoPet = ProtegidoPet::with('usuarios.contatos', 'usuarios.enderecos')->findOrFail($id);
            return response()->json($protegidoPet, 200);
        } catch (Exception $e) {
            return response()->json(['message' => 'Protegido Pet não encontrado'], 404);
        }
    }

    public function listarProtegidoPetUsuario($id)
    {
        try {
            $protegidos = ProtegidoPet::where('id_usuario', $id)->get();
            if ($protegidos->isEmpty()) {
                return response()->json(['message' => 'Usuarios não possue protegidoo Pet'], 404);
            }
            return response()->json($protegidos, 200);
        } catch (Exception $e) {
            return response()->json(['message' => 'Protegido Pet não encontrado'], 404);
        }
    }
    public function deletarProtegidoPet($id)
    {
        try {
            $protegidoPet = ProtegidoPet::findOrFail($id);
            $protegidoPet->delete();
            return response()->json(['message' => 'Protegido Pet deletado com sucesso'], 200);
        } catch (Exception $e) {
            return response()->json(['message' => 'Protegido Pet não encontrado'], 404);
        }
    }
}
