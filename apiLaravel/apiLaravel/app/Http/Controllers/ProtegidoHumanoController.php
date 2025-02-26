<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\ProtegidoHumano;
use App\Models\Usuario;
use Carbon\Carbon;
use Illuminate\Validation\ValidationException;
use Exception;

class ProtegidoHumanoController extends Controller
{
    public function salvarProtegidoHumano(Request $request)
    {

        try {
            Usuario::findorFail($request->usuario['idUsuario']);
        } catch (Exception $e) {
            return response()->json(['error' => 'Usuário não encontrado'], 404);
        }
        try {
            $this->validate($request, [
                'nome_protegido' => 'required',
                'peso' => 'required',
                'altura' => 'required'
            ]);
        } catch (ValidationException $e) {
            return response()->json($e->errors(), 400);
        }

        if ($request->isJson()) {
            $data = $request->json()->all();
            $protegidoHumano = new ProtegidoHumano();
            $protegidoHumano->nome_protegido = $data['nome_protegido'];
            $protegidoHumano->dataNascimento = $data['dataNascimento'];
            $protegidoHumano->peso = $data['peso'];
            $protegidoHumano->alergias = $data['alergias'];
            $protegidoHumano->altura = $data['altura'];
            $protegidoHumano->patologias = $data['patologias'];
            $protegidoHumano->uso_medicacoes = $data['uso_medicacoes'];
            $protegidoHumano->observacoes_gerais = $data['observacoes_gerais'];
            $protegidoHumano->imagem = $data['imagem'];
            $protegidoHumano->id_usuario = $request->usuario['idUsuario'];
            $protegidoHumano->save();
            return response()->json($protegidoHumano, 201);
        }
        return response()->json(['error' => 'Unauthorized'], 401, []);
    }

    public function editarProtegidoHumano(Request $request)
    {
        try {
            $this->validate($request, [
                'nome_protegido' => 'required',
                'peso' => 'required',
                'altura' => 'required',
                'id_protegido_humano' => 'required',
                'usuario.idUsuario' => 'required'
            ]);
        } catch (ValidationException $e) {
            return response()->json($e->errors(), 400);
        }
        try {
            $usuario = Usuario::findOrFail($request->usuario['idUsuario']);
        } catch (Exception $e) {
            return response()->json(['error' => 'Usuário não encontrado'], 404);
        }
        try {
            $protegidoHumano = ProtegidoHumano::findOrFail($request->id_protegido_humano);
            if ($protegidoHumano->id_usuario != $request->usuario['idUsuario']) {
                return response()->json(['error' => 'Protegido não pertence ao usuário'], 400);
            }

            $request->merge(["dataAlteracao" => Carbon::now()]);


            $protegidoHumano->update($request->all());
            return response()->json($protegidoHumano, 200);
        } catch (Exception $e) {
            return response()->json(['error' => 'Protegido não encontrado'], 404);
        }
    }

    public function listarProtegidoHumanoUsuario($id)
    {
        try {
            Usuario::findOrFail($id);
        } catch (Exception $e) {
            return response()->json(['error' => 'Usuário não encontrado'], 404);
        }
        $protegidos = ProtegidoHumano::where('id_usuario', $id)->get();
        return response()->json($protegidos, 200);
    }
    public function pesquisarProtegidoHumanoPorID($id)
    {
        try {
            $protegido = ProtegidoHumano::with('usuarios.contatos', 'usuarios.enderecos')->findOrFail($id);
            return response()->json($protegido, 200);
        } catch (Exception $e) {
            return response()->json(['error' => 'Protegido não encontrado'], 404);
        }
    }
    public function deletarProtegidoHumano($id)
    {
        try {
            $protegido = ProtegidoHumano::findOrFail($id);
            $protegido->delete();
            return response()->json(['success' => 'Protegido deletado com sucesso'], 200);
        } catch (Exception $e) {
            return response()->json(['error' => 'Protegido não encontrado'], 404);
        }
    }
}
