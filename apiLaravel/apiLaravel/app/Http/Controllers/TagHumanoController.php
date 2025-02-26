<?php

namespace App\Http\Controllers;

use App\Models\ProtegidoHumano;
use Illuminate\Http\Request;
use App\Models\TagHumano;
use Exception;
use App\Models\Usuario;

class TagHumanoController extends Controller
{
    public function gravarTag(Request $request)
    {
        try {
            $tag = TagHumano::where('idTag', $request->idTag)->first();
            if ($tag) {
                return response()->json(['message' => 'Tag já cadastrada']);
            }
        } catch (Exception $e) {
            return response()->json(['message' => 'Erro ao cadastrar tag']);
        }
        try {
            $protegido =  ProtegidoHumano::findOrFail($request->idProtegido);
        } catch (Exception $e) {
            return response()->json(['message' => 'Protegido não cadastrado'], 404);
        }

        try {
            $idUsuario = Usuario::findOrFail($protegido->id_usuario);
        } catch (Exception $e) {
            return response()->json(['message' => 'Usuário não encontrado'], 404);
        }

        $tag = new TagHumano();
        $tag->idTag = $request->idTag;
        $tag->idProtegido = $request->idProtegido;
        $tag->id_usuario = $idUsuario->id_usuario;
        $tag->save();
        return response()->json($tag);
    }

    public function editarTag(Request $request)
    {
        try {
            $tag = TagHumano::findOrFail($request->idTag);
        } catch (\Exception $e) {
            return response()->json(["Tag não cadastrada"], 401);
        }
        try {
            $protegido = ProtegidoHumano::findOrFail($request->idProtegido);
            if ($protegido->id_usuario != $tag->id_usuario)
                return response()->json(['Não foi possivel salvar o protegido por que a pulseira é de outro usuário'], 401);
        } catch (\Exception $e) {
            return response()->json("Protegido não encontrado", 400);
        }
        $tag->idTag = $request->idTag;
        $tag->idProtegido = $request->idProtegido;
        $tag->save();
        return response()->json($tag);
    }

    public function tagID($id)
    {
        $tag = TagHumano::find($id);
        $protegido = ProtegidoHumano::find($tag->idProtegido);
        $usuario = Usuario::with('contatos', 'enderecos')->find($tag->id_usuario);
        return response()->json(["protegido" => $protegido, 'usuario' => $usuario], 200);
    }

    public function listarTagID($id)
    {
        try {
            $tag = TagHumano::where('id_usuario', $id)->get();
            return response()->json($tag, 200);
        } catch (\Exception $e) {
            return response()->json(["mensagem" => $e], 404);
        }
    }

    public function liberarTag($id)
    {
        try {
            $tag = TagHumano::findOrFail($id);
        } catch (\Exception $e) {
            return response()->json(['Tag ID não encontrado'], 404);
        }
        $tag->delete();
        return response()->json(['message' => 'Tag Liberada com sucesso'], 200);
    }
}
