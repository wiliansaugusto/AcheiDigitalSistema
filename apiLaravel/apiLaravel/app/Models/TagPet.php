<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;
use League\CommonMark\Extension\Table\Table;

class TagPet extends Model
{
    protected $table = "tag_pets";
    public $timestamps = false;
    protected $primaryKey = 'idTagPet';
    protected $fillable = [
        'id_protegido_pet',
        'id_usuario'
    ];

    public function protegido()
    {
        return $this->belongsTo(ProtegidoPet::class,  'id_protegido_pet', 'id_protegido_pet');
    }
    public function usuarios()
    {
        return $this->belongsTo(Usuario::class, 'id_usuario', 'id_usuario');
    }
}
