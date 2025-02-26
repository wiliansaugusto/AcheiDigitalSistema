<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class TagHumano extends Model
{

    protected $table = 'tag_humanos';
    public $timestamps = false;
    protected $primaryKey = 'idTag';


    protected $fillable = [
        'idProtegido',
        'id_usuario'
    ];

    public function protegido()
    {
        return $this->belongsTo(ProtegidoHumano::class,  'idProtegido', 'id_protegido_humano');
    }
    public function usuarios()
    {
        return $this->belongsTo(Usuario::class, 'id_usuario', 'id_usuario');
    }
}
