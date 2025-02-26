<?php

namespace App\Http\Controllers;

use App\Models\ProtegidoPet;
use App\Models\TagPet;
use App\Models\Usuario;
use Doctrine\Common\Annotations\Annotation\Target;
use Illuminate\Http\Request;

class TagPetController extends Controller
{
    public function gravarTagPet(Request $request)
    {
        try {
            $tag = TagPet::where('idTagPet', $request->idTagPet)->first();
            if ($tag) {
                return response()->json(['message' => 'Tag já cadastrada']);
            }
        } catch (\Exception $e) {
            return response()->json(['message' => 'Erro ao cadastrar tag']);
        }
        try {
            $protegido =  ProtegidoPet::findOrFail($request->id_protegido_pet);
        } catch (\Exception $e) {
            return response()->json(['message' => 'Protegido não cadastrado'], 404);
        }

        try {
            $idUsuario = Usuario::findOrFail($protegido->id_usuario);
        } catch (\Exception $e) {
            return response()->json(['message' => 'Usuário não encontrado'], 404);
        }

        $tag = new TagPet();
        $tag->idTagPet = $request->idTagPet;
        $tag->id_protegido_pet = $request->id_protegido_pet;
        $tag->id_usuario = $idUsuario->id_usuario;
        $tag->save();
        return response()->json($tag);
    }

    public function editarTagPet(Request $request)
    {
        try {
            $tag = TagPet::findOrFail($request->idTagPet);
        } catch (\Exception $e) {
            return response()->json(["Tag não cadastrada"], 401);
        }
        try {
            $protegido = ProtegidoPet::find($request->id_protegido_pet);
            if ($protegido->id_usuario != $tag->id_usuario)
                return response()->json(['Não foi possivel salvar o protegido por que a pulseira é de outro usuário'], 401);
        } catch (\Exception $e) {
            return response()->json("Protegido não encontrado", 400);
        }
        $tag->idTagPet = $request->idTagPet;
        $tag->id_protegido_pet = $request->id_protegido_pet;
        $tag->save();
        return response()->json($tag);
    }

    public function tagIDPet($id)
    {
        $tag = TagPet::find($id);
        $protegido = ProtegidoPet::find($tag->id_protegido_pet);
        $usuario = Usuario::with('contatos', 'enderecos')->find($tag->id_usuario);
        return response()->json(["protegido" => $protegido, 'usuario' => $usuario], 200);
    }

    public function listarTagIDPet($id)
    {
        try {
            $tag = TagPet::where('id_usuario', $id)->get();
            return response()->json($tag, 200);
        } catch (\Exception $e) {
            return response()->json(["mensagem" => $e], 404);
        }
    }

    public function liberarTagPet($id)
    {
        try {
            $tag = TagPet::findOrFail($id);
        } catch (\Exception $e) {
            return response()->json(['Tag Pet ID não encontrado'], 404);
        }
        $tag->delete();
        return response()->json(['message' => 'Tag Pet Liberada com sucesso'], 200);
    }
}
