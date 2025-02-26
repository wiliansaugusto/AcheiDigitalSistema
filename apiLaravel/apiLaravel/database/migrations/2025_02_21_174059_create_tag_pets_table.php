<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('tag_pets', function (Blueprint $table) {
            $table->bigIncrements('idTagPet');
            $table->unsignedBigInteger('id_protegido_pet');
            $table->foreign('id_protegido_pet')->references('id_protegido_pet')->on('protegido_pets')->onDelete('cascade');
            $table->unsignedBigInteger('id_usuario');
            // Define fa oreign key
            $table->foreign('id_usuario')
                ->references('id_usuario')
                ->on('usuarios')
                ->onDelete('cascade');
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('tag_pets');
    }
};
