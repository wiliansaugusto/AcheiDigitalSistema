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
        Schema::create('protegido_pets', function (Blueprint $table) {
            $table->bigIncrements('id_protegido_pet');
            $table->string('nome_protegido_pet', 100);
            $table->string('peso', 6);
            $table->string('raca')->nullable();
            $table->string('especie')->nullable();
            $table->longText('patologias')->nullable();
            $table->longText('uso_medicacoes')->nullable();
            $table->longText('observacoes_gerais')->nullable();
            $table->longText('imagem')->nullable();
            $table->longText('alergias')->nullable();
            $table->date('dataNascimento')->nullable();
            $table->timestamp('dataCriacao')->useCurrent();
            $table->timestamp('dataAlteracao')->nullable();

            $table->unsignedBigInteger('id_usuario'); // Chave estrangeira

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
        Schema::dropIfExists('protegido_pets');
    }
};
